package com.sparta.orderservice.order.application.facade.v1;

import com.sparta.orderservice.order.application.usecase.v1.OrderUseCaseV1;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrdersPostDtoApiV1;

import com.sparta.orderservice.order.presentation.dto.v1.response.ResOrderPostDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderFacadeV1 {

    private final OrderUseCaseV1 orderUseCaseV1;

    public void updateBy(ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1, UUID orderId) {
        orderUseCaseV1.updateBy(reqOrderPutDtoApiV1, orderId);
    }

    public OrderEntity getOrderBy(UUID orderId){
        return orderUseCaseV1.getOrderBy(orderId);
    }

    public Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size) {
        return orderUseCaseV1.getOrdersByUserId(userId, page, size);
    }

    public ResOrderPostDtoApiV1 processOrder(Long userId, ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1){
        return orderUseCaseV1.processOrder(userId, reqOrdersPostDtoApiV1);
    }
}
