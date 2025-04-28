package com.sparta.orderservice.order.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.themepark.common.application.dto.ResDTO;
import com.github.themepark.common.application.exception.CustomException;
import com.sparta.orderservice.order.application.dto.reponse.ResProductGetByIdDTOApiV1;
import com.sparta.orderservice.order.application.exception.OrderExceptionCode;
import com.sparta.orderservice.order.application.facade.OrderFacade;
import com.sparta.orderservice.order.application.usecase.OrderUseCase;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.infrastructure.feign.ProductFeignClientApiV1;
import com.sparta.orderservice.order.infrastructure.kafka.service.KafkaService;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.response.ResOrderPostDtoApiV1;
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
import java.util.concurrent.TimeUnit;


@Service
public class OrderService implements OrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductFeignClientApiV1 productFeignClientApiV1;
    private final RedisTemplate<String, ResProductGetByIdDTOApiV1> productRedisTemplate;
    private final RedisTemplate<String, String> stockRedisTemplate;
    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;
    private final Tracer tracer;

    public OrderService(
            OrderRepository orderRepository,
            ProductFeignClientApiV1 productFeignClientApiV1,
            @Qualifier("ProductRedisTemplate") RedisTemplate<String, ResProductGetByIdDTOApiV1> productRedisTemplate,
            @Qualifier("stockRedisTemplate") RedisTemplate<String, String> stockRedisTemplate,
            KafkaService kafkaService,
            ObjectMapper objectMapper,
            Tracer tracer
    ) {
        this.orderRepository = orderRepository;
        this.productFeignClientApiV1 = productFeignClientApiV1;
        this.productRedisTemplate = productRedisTemplate;
        this.stockRedisTemplate = stockRedisTemplate;
        this.kafkaService = kafkaService;
        this.objectMapper = objectMapper;
        this.tracer = tracer;
    }

    @Override
    public ResOrderPostDtoApiV1 processOrder(Long userId, ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1){
        // 🔥 Redis 조회 + 재고관리 Span 시작
        Span redisSpan = tracer.nextSpan().name("Redis 처리").start();
        try (Tracer.SpanInScope ws = tracer.withSpan(redisSpan)) {
            String productId = reqOrdersPostDtoApiV1.getOrder().getProductId().toString();
            String redisProductKey = "stock : " + productId;
            String redisStockKey = "key : " + productId;

            Object raw = productRedisTemplate.opsForValue().get(redisProductKey);
            ResProductGetByIdDTOApiV1 product = objectMapper.convertValue(raw, ResProductGetByIdDTOApiV1.class);

            if (product == null) {
                ResDTO<ResProductGetByIdDTOApiV1> resProductGetByIdDTOApiV1ResDTO = productFeignClientApiV1.getBy(reqOrdersPostDtoApiV1.getOrder().getProductId());
                productRedisTemplate.opsForValue().set(redisProductKey, resProductGetByIdDTOApiV1ResDTO.getData());

                if (Objects.equals(resProductGetByIdDTOApiV1ResDTO.getData().getProduct().getProductType(), "EVENT")) {
                    Integer quantity = resProductGetByIdDTOApiV1ResDTO.getData().getProduct().getLimitQuantity();
                    stockRedisTemplate.opsForValue().set(redisStockKey, String.valueOf(quantity), 10, TimeUnit.MINUTES);

                    Long remain = stockRedisTemplate.opsForValue().decrement(redisStockKey);
                    if (remain == null || remain < 0) {
                        stockRedisTemplate.opsForValue().increment(redisStockKey);
                        throw new CustomException(OrderExceptionCode.ORDER_OUT_OF_STOCK);
                    }
                    kafkaService.sendOrderAsync("stock-decrease-topic", productId);
                }
            } else {
                if (Objects.equals(product.getProduct().getProductType(), "EVENT")) {
                    Long remain = stockRedisTemplate.opsForValue().decrement(redisStockKey);
                    if (remain == null || remain < 0) {
                        stockRedisTemplate.opsForValue().increment(redisStockKey);
                        throw new CustomException(OrderExceptionCode.ORDER_OUT_OF_STOCK);
                    }
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
            orderEntity = OrderEntity.createOrder(reqOrdersPostDtoApiV1, userId);
            orderRepository.save(orderEntity);
        } finally {
            dbSpan.end(); // DB Span 종료
        }

        return ResOrderPostDtoApiV1.of(orderEntity);
    }


    @Override
    public void updateBy(ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1, UUID orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId);
        OrderEntity.updateOrder(orderEntity, reqOrderPutDtoApiV1);
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
