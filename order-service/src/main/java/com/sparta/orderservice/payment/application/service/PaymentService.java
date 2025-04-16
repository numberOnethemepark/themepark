package com.sparta.orderservice.payment.application.service;

import com.github.themepark.common.application.exception.ExceptionResponse;
import com.github.themepark.common.application.exception.GlobalExceptionCode;
import com.sparta.orderservice.payment.application.dto.request.ReqPaymentTossDto;
import com.sparta.orderservice.payment.application.dto.response.ResPaymentTossDto;
import com.sparta.orderservice.payment.application.usercase.PaymentUseCase;
import com.sparta.orderservice.payment.domain.entity.Payment;
import com.sparta.orderservice.payment.domain.repository.PaymentRepository;
import com.sparta.orderservice.payment.infrastructure.client.TossPaymentsClient;
import com.sparta.orderservice.payment.presentation.dto.request.ReqPaymentPostDtoApiV1;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        ResPaymentTossDto tossRes = tossPaymentsClient.confirmPayment(ReqPaymentTossDto
                .of(reqPaymentPostDtoApiV1.getPayment().getOrderId()
                        , reqPaymentPostDtoApiV1.getPayment().getAmount()
                        , reqPaymentPostDtoApiV1.getPayment().getPaymentKey()
                )
        );

        if (tossRes.getFailure() != null){
            throw new RuntimeException("데이터가 존재하지 않습니다.");
        }

        Payment payment = Payment.builder()
                .paymentKey(Objects.requireNonNull(tossRes).getPaymentKey())
                .orderId(tossRes.getOrderId())
                .paymentStatus(tossRes.getStatus())
                .cardNumber(tossRes.getCard().getNumber())
                .amount(tossRes.getCard().getAmount())
                .build();

        paymentRepository.save(payment);
    }
}
