package com.sparta.orderservice.order.application.facade.v3;

import com.sparta.orderservice.order.application.usecase.v3.OrderUseCaseV3;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.v2.response.ResOrderPostDtoApiV2;
import com.sparta.orderservice.order.presentation.dto.v3.request.ReqOrderPutDtoApiV3;
import com.sparta.orderservice.order.presentation.dto.v3.request.ReqOrdersPostDtoApiV3;
import com.sparta.orderservice.order.presentation.dto.v3.response.ResOrderPostDtoApiV3;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderFacadeV3 {

    private final OrderUseCaseV3 orderUseCaseV3;

    public void updateBy(ReqOrderPutDtoApiV3 reqOrderPutDtoApiV3, UUID orderId) {
        orderUseCaseV3.updateBy(reqOrderPutDtoApiV3, orderId);
    }

    public OrderEntity getOrderBy(UUID orderId){
        return orderUseCaseV3.getOrderBy(orderId);
    }

    public Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size) {
        return orderUseCaseV3.getOrdersByUserId(userId, page, size);
    }

    public ResOrderPostDtoApiV3 processOrder(Long userId, ReqOrdersPostDtoApiV3 reqOrdersPostDtoApiV3){
        return orderUseCaseV3.processOrder(userId, reqOrdersPostDtoApiV3);
    }
}
