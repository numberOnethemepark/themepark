package com.sparta.orderservice.payment.application.facade;

import com.sparta.orderservice.payment.application.usercase.PaymentUseCase;
import com.sparta.orderservice.payment.domain.entity.PaymentEntity;
import com.sparta.orderservice.payment.presentation.dto.request.ReqPaymentPostDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentUseCase paymentUseCase;

    public void createPayment(ReqPaymentPostDtoApiV1 reqPaymentPostDtoApiV1) {
        paymentUseCase.createPayment(reqPaymentPostDtoApiV1);
    }

    public PaymentEntity getBy(UUID orderId){
        return paymentUseCase.getBy(orderId);
    }

}
