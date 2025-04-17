package com.sparta.orderservice.payment.domain.repository;

import com.sparta.orderservice.payment.domain.entity.Payment;

public interface PaymentRepository {
    void save(Payment payment);
}
