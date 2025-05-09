package com.sparta.orderservice.payment.application.usercase;

import com.sparta.orderservice.payment.domain.entity.PaymentEntity;
import com.sparta.orderservice.payment.presentation.dto.request.ReqPaymentPostDtoApiV1;

import java.util.UUID;

public interface PaymentUseCase {
    void createPayment(ReqPaymentPostDtoApiV1 reqPaymentPostDtoApiV1);
    PaymentEntity getBy(UUID paymentId);
}
