package com.sparta.orderservice.order.infrastructure.repository;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataJpaOrderRepository extends JpaRepository<OrderEntity, UUID> {
    Page<OrderEntity> findByUserId(Long userId, Pageable pageable);
}
