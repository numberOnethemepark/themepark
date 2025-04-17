package com.sparta.orderservice.payment.infrastructure.repository;

import com.sparta.orderservice.payment.domain.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataJpaPaymentRepository extends JpaRepository<PaymentEntity, UUID> {
}
