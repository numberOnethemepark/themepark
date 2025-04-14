package com.sparta.orderservice.order.domain.repository;

import com.sparta.orderservice.order.domain.entity.Order;

public interface OrderRepository {
    void save(Order order);
}
