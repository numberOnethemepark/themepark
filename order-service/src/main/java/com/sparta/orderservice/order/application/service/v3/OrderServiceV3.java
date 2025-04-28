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
        // 🔥 Redis 조회 + 재고관리 Span 시작
        Span redisSpan = tracer.nextSpan().name("Redis 처리").start();
        try (Tracer.SpanInScope ws = tracer.withSpan(redisSpan)) {
            String productId = reqOrdersPostDtoApiV3.getOrder().getProductId().toString();
            String redisProductKey = "stock : " + productId;

            Object raw = productRedisTemplate.opsForValue().get(redisProductKey);
            ResProductGetByIdDTOApi product = objectMapper.convertValue(raw, ResProductGetByIdDTOApi.class);

            if (product == null) {
                ResDTO<ResProductGetByIdDTOApi> resProductGetByIdDTOApiV3ResDTO = productFeignClientApi.getBy(reqOrdersPostDtoApiV3.getOrder().getProductId());
                productRedisTemplate.opsForValue().set(redisProductKey, resProductGetByIdDTOApiV3ResDTO.getData());

                if (Objects.equals(resProductGetByIdDTOApiV3ResDTO.getData().getProduct().getProductType(), "EVENT")) {
                    productFeignClientApi.getStockById(reqOrdersPostDtoApiV3.getOrder().getProductId());
                    kafkaService.sendOrderAsync("stock-decrease-topic", productId);
                }
            } else {
                if (Objects.equals(product.getProduct().getProductType(), "EVENT")) {
                    productFeignClientApi.getStockById(reqOrdersPostDtoApiV3.getOrder().getProductId());
                    kafkaService.sendOrderAsync("stock-decrease-topic", productId);
                }
            }
        } finally {
            redisSpan.end(); // Redis Span 종료
        }

        // 🔥 DB 저장 Span 시작
        Span dbSpan = tracer.nextSpan().name("DB 저장").start();
        OrderEntity orderEntity;
        try (Tracer.SpanInScope ws = tracer.withSpan(dbSpan)) {
            orderEntity = OrderEntity.createOrder(reqOrdersPostDtoApiV3.getOrder().getProductId(),
                    reqOrdersPostDtoApiV3.getOrder().getAmount(),
                    reqOrdersPostDtoApiV3.getOrder().getSlackId(),
                    userId);
            orderRepository.save(orderEntity);
        } finally {
            dbSpan.end(); // DB Span 종료
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
