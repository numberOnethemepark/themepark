package com.sparta.orderservice.payment.infrastructure.repository;

import com.sparta.orderservice.payment.domain.entity.Payment;
import com.sparta.orderservice.payment.domain.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPaymentRepository implements PaymentRepository {

    private final SpringDataJpaPaymentRepository jpaPaymentRepository;

    public JpaPaymentRepository(SpringDataJpaPaymentRepository jpaPaymentRepository) {
        this.jpaPaymentRepository = jpaPaymentRepository;
    }

    @Override
    public void save(Payment payment){
        jpaPaymentRepository.save(payment);
    }

}
