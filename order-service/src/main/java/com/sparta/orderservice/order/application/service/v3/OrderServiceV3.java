package com.sparta.orderservice.order.application.service.v3;


import com.github.themepark.common.application.exception.CustomException;
import com.sparta.orderservice.order.application.dto.reponse.ResProductGetByIdDTOApi;
import com.sparta.orderservice.order.application.exception.OrderExceptionCode;
import com.sparta.orderservice.order.application.helper.OrderTransactionHelper;
import com.sparta.orderservice.order.application.usecase.v3.OrderUseCaseV3;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.presentation.dto.v3.request.ReqOrderPutDtoApiV3;
import com.sparta.orderservice.order.presentation.dto.v3.request.ReqOrdersPostDtoApiV3;
import com.sparta.orderservice.order.presentation.dto.v3.response.ResOrderPostDtoApiV3;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderServiceV3 implements OrderUseCaseV3 {

    private final OrderRepository orderRepository;
    private final OrderTransactionHelper orderTransactionHelper;
    private final Tracer tracer;

    @Override
    public ResOrderPostDtoApiV3 processOrder(Long userId, ReqOrdersPostDtoApiV3 reqOrdersPostDtoApiV3){
        String productId = reqOrdersPostDtoApiV3.getOrder().getProductId().toString();
        String redisProductKey = "stock : " + productId;
        ResProductGetByIdDTOApi product;
        OrderEntity orderEntity = null;
        boolean isEventProduct = false;

        Span rootSpan = tracer.nextSpan().name("Order 전체 프로세스").start();
        try (Tracer.SpanInScope ws = tracer.withSpan(rootSpan)) {

            // transaction 1 - Redis 조회
            Span redisSpan = tracer.nextSpan().name("Redis 조회").start();
            try (Tracer.SpanInScope redisScope = tracer.withSpan(redisSpan)) {
                product = orderTransactionHelper.getRedis(redisProductKey);
            } finally {
                redisSpan.end();
            }

            // transaction 2 - 상품 타입 확인 및 Redis 저장
            Span checkProductSpan = tracer.nextSpan().name("상품 타입 확인 + redis 저장").start();
            try (Tracer.SpanInScope checkProductScope = tracer.withSpan(checkProductSpan)) {
                isEventProduct = orderTransactionHelper.checkProduct(product, reqOrdersPostDtoApiV3, redisProductKey);
            } finally {
                checkProductSpan.end();
            }

            // transaction 3 - DB 저장
            Span dbSpan = tracer.nextSpan().name("DB 저장").start();
            try (Tracer.SpanInScope dbScope = tracer.withSpan(dbSpan)) {
                orderEntity = orderTransactionHelper.createOrder(userId, reqOrdersPostDtoApiV3);
            } finally {
                dbSpan.end();
            }

            // transaction 4 - EVENT 상품 재고 확인 및 Kafka 전송
            Span eventKafkaSpan = tracer.nextSpan().name("EVENT 상품이면 재고 확인 + Kafka 전송").start();
            try (Tracer.SpanInScope eventKafkaScope = tracer.withSpan(eventKafkaSpan)) {
                orderTransactionHelper.decreaseStock(orderEntity.getOrderId().toString(), productId, isEventProduct);
            } finally {
                eventKafkaSpan.end();
            }

            return ResOrderPostDtoApiV3.of(orderEntity);

        } catch (Exception ex) {
            Objects.requireNonNull(tracer.currentSpan()).tag("error", ex.getMessage());

            // 보상 트랜잭션 시작 (역순 실행)
            if (orderEntity != null) {
                orderTransactionHelper.cancelOrder(orderEntity); // DB 롤백
            }
            if (isEventProduct) {
                orderTransactionHelper.restoreStock(productId); // 재고 복원
            }

            throw new CustomException(OrderExceptionCode.ORDER_CANCEL);
        } finally {
            rootSpan.end();
        }
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
