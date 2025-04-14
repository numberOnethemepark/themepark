package com.sparta.orderservice.payment.infrastructure.repository;

import com.sparta.orderservice.payment.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
