package com.sparta.orderservice.payment.domain.repository;

import com.sparta.orderservice.payment.domain.entity.PaymentEntity;

import java.util.UUID;

public interface PaymentRepository {
    void save(PaymentEntity paymentEntity);

    PaymentEntity findById(UUID paymentId);
}
