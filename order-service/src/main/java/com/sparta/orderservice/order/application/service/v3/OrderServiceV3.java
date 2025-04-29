package com.sparta.orderservice.order.application.service.v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.themepark.common.application.dto.ResDTO;
import com.sparta.orderservice.order.application.dto.reponse.ResProductGetByIdDTOApi;
import com.sparta.orderservice.order.application.usecase.v3.OrderUseCaseV3;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.infrastructure.feign.ProductFeignClientApi;
import com.sparta.orderservice.order.infrastructure.kafka.service.KafkaService;
import com.sparta.orderservice.order.presentation.dto.v3.request.ReqOrderPutDtoApiV3;
import com.sparta.orderservice.order.presentation.dto.v3.request.ReqOrdersPostDtoApiV3;
import com.sparta.orderservice.order.presentation.dto.v3.response.ResOrderPostDtoApiV3;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;


@Service
public class OrderServiceV3 implements OrderUseCaseV3 {

    private final OrderRepository orderRepository;
    private final ProductFeignClientApi productFeignClientApi;
    private final RedisTemplate<String, ResProductGetByIdDTOApi> productRedisTemplate;
    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;
    private final Tracer tracer;

    public OrderServiceV3(
            OrderRepository orderRepository,
            ProductFeignClientApi productFeignClientApi,
            @Qualifier("ProductRedisTemplate") RedisTemplate<String, ResProductGetByIdDTOApi> productRedisTemplate,
            KafkaService kafkaService,
            ObjectMapper objectMapper,
            Tracer tracer
    ) {
        this.orderRepository = orderRepository;
        this.productFeignClientApi = productFeignClientApi;
        this.productRedisTemplate = productRedisTemplate;
        this.kafkaService = kafkaService;
        this.objectMapper = objectMapper;
        this.tracer = tracer;
    }

    @Override
    public ResOrderPostDtoApiV3 processOrder(Long userId, ReqOrdersPostDtoApiV3 reqOrdersPostDtoApiV3){
        String productId = reqOrdersPostDtoApiV3.getOrder().getProductId().toString();
        String redisProductKey = "stock : " + productId;

        // 🔥 Redis 조회 + 재고관리 Span 시작
        Span redisSpan = tracer.nextSpan().name("Redis 처리").start();
        ResProductGetByIdDTOApi product = null;
        try (Tracer.SpanInScope ws = tracer.withSpan(redisSpan)) {
            Object raw = productRedisTemplate.opsForValue().get(redisProductKey);
            if (raw != null) {
                product = objectMapper.convertValue(raw, ResProductGetByIdDTOApi.class);
            }
        } finally {
            redisSpan.end();
        }

        // 상품 타입 확인
        boolean isEventProduct = false;
        if (product == null) {
            // Redis 캐시 미스 → Product 서비스 호출 후 캐싱
            ResDTO<ResProductGetByIdDTOApi> resProduct = productFeignClientApi.getBy(reqOrdersPostDtoApiV3.getOrder().getProductId());
            product = resProduct.getData();
            productRedisTemplate.opsForValue().set(redisProductKey, product, Duration.ofMinutes(10)); // TTL 10분 예시
        }
        isEventProduct = "EVENT".equals(product.getProduct().getProductType());

        // 🔥 EVENT 상품이면 재고 확인 & Kafka 전송
        if (isEventProduct) {
            productFeignClientApi.getStockById(reqOrdersPostDtoApiV3.getOrder().getProductId());
            kafkaService.sendOrderAsync("stock-decrease-topic", productId);
        }

        // 🔥 DB 저장 Span 시작
        Span dbSpan = tracer.nextSpan().name("DB 저장").start();
        OrderEntity orderEntity;
        try (Tracer.SpanInScope ws = tracer.withSpan(dbSpan)) {
            orderEntity = OrderEntity.createOrder(
                    reqOrdersPostDtoApiV3.getOrder().getProductId(),
                    reqOrdersPostDtoApiV3.getOrder().getAmount(),
                    reqOrdersPostDtoApiV3.getOrder().getSlackId(),
                    userId
            );
            orderRepository.save(orderEntity);
        } finally {
            dbSpan.end();
        }

        return ResOrderPostDtoApiV3.of(orderEntity);
    }


    @Override
    public void updateBy(ReqOrderPutDtoApiV3 reqOrderPutDtoApiV3, UUID orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId);
        OrderEntity.updateOrder(orderEntity, reqOrderPutDtoApiV3.getOrder().getPaymentId(), reqOrderPutDtoApiV3.getOrder().getPaymentStatus());
    }

    @Override
    public OrderEntity getOrderBy(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return orderRepository.findByUserId(userId, pageable);
    }

}
