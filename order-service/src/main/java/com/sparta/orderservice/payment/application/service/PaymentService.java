package com.sparta.orderservice.payment.application.service;

import com.sparta.orderservice.payment.application.dto.request.ReqPaymentTossDto;
import com.sparta.orderservice.payment.application.dto.response.ResPaymentTossDto;
import com.sparta.orderservice.payment.application.usercase.PaymentUseCase;
import com.sparta.orderservice.payment.domain.entity.Payment;
import com.sparta.orderservice.payment.domain.repository.PaymentRepository;
import com.sparta.orderservice.payment.infrastructure.client.TossPaymentsClient;
import com.sparta.orderservice.payment.presentation.dto.request.ReqPaymentPostDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

    private final TossPaymentsClient tossPaymentsClient;
    private final PaymentRepository paymentRepository;

    @Override
    public void createPayment(ReqPaymentPostDtoApiV1 reqPaymentPostDtoApiV1){
        ResponseEntity<ResPaymentTossDto> tossRes = tossPaymentsClient.confirmPayment(ReqPaymentTossDto
                .of(reqPaymentPostDtoApiV1.getPayment().getOrderId()
                        , reqPaymentPostDtoApiV1.getPayment().getAmount()
                )
        );

        Payment payment = Payment.builder()
                .paymentKey(Objects.requireNonNull(tossRes.getBody()).getPayment().getPaymentKey())
                .orderId(tossRes.getBody().getPayment().getOrderId())
                .paymentStatus(tossRes.getBody().getPayment().getStatus())
                .cardNumber(tossRes.getBody().getPayment().getCard().getNumber())
                .amount(tossRes.getBody().getPayment().getCard().getAmount())
                .build();

        paymentRepository.save(payment);
    }
}
