package com.sparta.orderservice.order.application.usecase;

import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;

public interface OrderUseCase {
    void createOrder(ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1);
}
