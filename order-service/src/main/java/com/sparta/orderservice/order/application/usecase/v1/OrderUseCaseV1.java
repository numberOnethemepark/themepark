package com.sparta.orderservice.order.application.usecase.v1;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.v1.response.ResOrderPostDtoApiV1;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface OrderUseCaseV1 {

    @Transactional
    void updateBy(ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1, UUID orderId);

    @Transactional
    OrderEntity getOrderBy(UUID orderId);

    @Transactional
    Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size);

    @Transactional
    ResOrderPostDtoApiV1 processOrder(Long userId, ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1);
}
