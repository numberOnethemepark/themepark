package com.sparta.orderservice.order.domain.repository;

import com.sparta.orderservice.order.domain.entity.OrderEntity;

public interface OrderRepository {
    void save(OrderEntity orderEntity);
}
