package com.sparta.orderservice.order.application.usecase;

import com.sparta.orderservice.order.domain.entity.Order;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;

public interface OrderUseCase {
    Order createOrder(ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1);
}
