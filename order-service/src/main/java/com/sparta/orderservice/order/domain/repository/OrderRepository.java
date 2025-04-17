package com.sparta.orderservice.order.domain.repository;

import com.sparta.orderservice.order.domain.entity.OrderEntity;

import java.util.UUID;

public interface OrderRepository {
    void save(OrderEntity orderEntity);

    OrderEntity findById(UUID orderId);
}
