package com.sparta.orderservice.order.application.usecase.v3;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.v3.request.ReqOrderPutDtoApiV3;
import com.sparta.orderservice.order.presentation.dto.v3.request.ReqOrdersPostDtoApiV3;
import com.sparta.orderservice.order.presentation.dto.v3.response.ResOrderPostDtoApiV3;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface OrderUseCaseV3 {

    @Transactional
    void updateBy(ReqOrderPutDtoApiV3 reqOrderPutDtoApiV3, UUID orderId);

    @Transactional
    OrderEntity getOrderBy(UUID orderId);

    @Transactional
    Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size);

    @Transactional
    ResOrderPostDtoApiV3 processOrder(Long userId, ReqOrdersPostDtoApiV3 reqOrdersPostDtoApiV3);
}
