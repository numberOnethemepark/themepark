package com.sparta.orderservice.order.application.usecase;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;

public interface OrderUseCase {
    OrderEntity createOrder(ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1);
}
