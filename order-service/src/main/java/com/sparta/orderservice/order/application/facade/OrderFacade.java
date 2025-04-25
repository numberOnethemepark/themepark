package com.sparta.orderservice.order.application.facade;

import com.sparta.orderservice.order.application.usecase.OrderUseCase;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;

import com.sparta.orderservice.order.presentation.dto.response.ResOrderPostDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderUseCase orderUseCase;

    public void updateBy(ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1, UUID orderId) {
        orderUseCase.updateBy(reqOrderPutDtoApiV1, orderId);
    }

    public OrderEntity getOrderBy(UUID orderId){
        return orderUseCase.getOrderBy(orderId);
    }

    public Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size) {
        return orderUseCase.getOrdersByUserId(userId, page, size);
    }

    public ResOrderPostDtoApiV1 processOrder(Long userId, ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1){
        return orderUseCase.processOrder(userId, reqOrdersPostDtoApiV1);
    }
}
