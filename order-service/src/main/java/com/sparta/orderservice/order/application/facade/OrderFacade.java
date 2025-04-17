package com.sparta.orderservice.order.application.facade;

import com.sparta.orderservice.order.application.usecase.OrderUseCase;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderUseCase orderUseCase;

    public OrderEntity createOrder(ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1) {
        return orderUseCase.createOrder(reqOrdersPostDtoApiV1);
    }

    public void updateOrder(ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1, UUID orderId) {
        orderUseCase.updateOrder(reqOrderPutDtoApiV1, orderId);
    }
}
