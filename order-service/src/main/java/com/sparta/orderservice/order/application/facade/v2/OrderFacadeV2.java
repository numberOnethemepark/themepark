package com.sparta.orderservice.order.application.facade.v2;

import com.sparta.orderservice.order.application.usecase.v2.OrderUseCaseV2;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.v2.request.ReqOrderPutDtoApiV2;
import com.sparta.orderservice.order.presentation.dto.v2.request.ReqOrdersPostDtoApiV2;
import com.sparta.orderservice.order.presentation.dto.v2.response.ResOrderPostDtoApiV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderFacadeV2 {

    private final OrderUseCaseV2 orderUseCaseV2;

    public void updateBy(ReqOrderPutDtoApiV2 reqOrderPutDtoApiV2, UUID orderId) {
        orderUseCaseV2.updateBy(reqOrderPutDtoApiV2, orderId);
    }

    public OrderEntity getOrderBy(UUID orderId){
        return orderUseCaseV2.getOrderBy(orderId);
    }

    public Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size) {
        return orderUseCaseV2.getOrdersByUserId(userId, page, size);
    }

    public ResOrderPostDtoApiV2 processOrder(Long userId, ReqOrdersPostDtoApiV2 reqOrdersPostDtoApiV2){
        return orderUseCaseV2.processOrder(userId, reqOrdersPostDtoApiV2);
    }
}
