package com.sparta.orderservice.order.application.facade;

import com.sparta.orderservice.order.application.usecase.OrderUseCase;
import com.sparta.orderservice.order.domain.entity.Order;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.payment.application.facade.PaymentFacade;

import com.sparta.orderservice.payment.presentation.dto.request.ReqPaymentPostDtoApiV1;
import com.sparta.orderservice.payment.presentation.dto.request.ReqPaymentPutDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderUseCase orderUseCase;

    public Order createOrder(ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1) {
        return orderUseCase.createOrder(reqOrdersPostDtoApiV1);
    }
}
