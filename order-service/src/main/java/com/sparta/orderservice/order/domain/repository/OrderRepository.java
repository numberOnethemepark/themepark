package com.sparta.orderservice.order.domain.repository;

import com.sparta.orderservice.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
