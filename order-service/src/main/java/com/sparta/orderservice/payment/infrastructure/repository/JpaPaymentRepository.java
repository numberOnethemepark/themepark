package com.sparta.orderservice.payment.infrastructure.repository;

import com.github.themepark.common.application.exception.CustomException;
import com.sparta.orderservice.payment.application.exception.PaymentExceptionCode;
import com.sparta.orderservice.payment.domain.entity.PaymentEntity;
import com.sparta.orderservice.payment.domain.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaPaymentRepository implements PaymentRepository {

    private final SpringDataJpaPaymentRepository jpaPaymentRepository;

    public JpaPaymentRepository(SpringDataJpaPaymentRepository jpaPaymentRepository) {
        this.jpaPaymentRepository = jpaPaymentRepository;
    }

    @Override
    public void save(PaymentEntity paymentEntity){
        jpaPaymentRepository.save(paymentEntity);
    }

    @Override
    public PaymentEntity findById(UUID paymentId) { return jpaPaymentRepository.findById(paymentId).orElseThrow(() -> new CustomException(PaymentExceptionCode.PAYMENT_EXCEPTION_CODE_NOT_FOUND));}

}
