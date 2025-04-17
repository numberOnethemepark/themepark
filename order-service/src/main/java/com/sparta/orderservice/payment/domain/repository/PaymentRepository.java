package com.sparta.orderservice.payment.domain.repository;

import com.sparta.orderservice.payment.domain.entity.PaymentEntity;

public interface PaymentRepository {
    void save(PaymentEntity paymentEntity);
}
