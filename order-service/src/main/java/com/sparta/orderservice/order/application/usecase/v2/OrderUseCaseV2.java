package com.sparta.orderservice.order.application.usecase.v2;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.v1.response.ResOrderPostDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.v2.request.ReqOrderPutDtoApiV2;
import com.sparta.orderservice.order.presentation.dto.v2.request.ReqOrdersPostDtoApiV2;
import com.sparta.orderservice.order.presentation.dto.v2.response.ResOrderPostDtoApiV2;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface OrderUseCaseV2 {

    @Transactional
    void updateBy(ReqOrderPutDtoApiV2 reqOrderPutDtoApiV2, UUID orderId);

    @Transactional
    OrderEntity getOrderBy(UUID orderId);

    @Transactional
    Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size);

    @Transactional
    ResOrderPostDtoApiV2 processOrder(Long userId, ReqOrdersPostDtoApiV2 reqOrdersPostDtoApiV1);
}
