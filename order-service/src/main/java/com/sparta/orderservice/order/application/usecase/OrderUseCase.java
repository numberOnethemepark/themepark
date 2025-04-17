package com.sparta.orderservice.order.application.usecase;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.response.ResOrdersGetByIdDtoApiV1;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface OrderUseCase {
    @Transactional
    OrderEntity postBy(ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1, Long userId);

    @Transactional
    void updateBy(ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1, UUID orderId);

    @Transactional
    OrderEntity getOrderBy(UUID orderId);

    @Transactional
    Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size);
}
