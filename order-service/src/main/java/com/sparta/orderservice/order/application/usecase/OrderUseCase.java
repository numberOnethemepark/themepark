package com.sparta.orderservice.order.application.usecase;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface OrderUseCase {
    @Transactional
    OrderEntity createOrder(ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1);

    @Transactional
    void updateOrder(ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1, UUID orderId);

}
