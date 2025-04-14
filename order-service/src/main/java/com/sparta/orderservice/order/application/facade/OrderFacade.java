package com.sparta.orderservice.order.application.facade;

import com.sparta.orderservice.order.application.usecase.OrderUseCase;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderUseCase orderUseCase;

    public void createOrder(ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1) {
        orderUseCase.createOrder(reqOrdersPostDtoApiV1);
    }
}
