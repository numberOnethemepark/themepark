package com.sparta.orderservice.payment.application.usercase;

import com.sparta.orderservice.payment.presentation.dto.request.ReqPaymentPostDtoApiV1;

public interface PaymentUseCase {
    void createPayment(ReqPaymentPostDtoApiV1 reqPaymentPostDtoApiV1);
}
