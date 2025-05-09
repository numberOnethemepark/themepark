package com.sparta.orderservice.order.domain.repository;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderRepository {
    void save(OrderEntity orderEntity);
    void delete(OrderEntity orderEntity);

    OrderEntity findById(UUID orderId);

    Page<OrderEntity> findByUserId(Long userId, Pageable pageable);
}
