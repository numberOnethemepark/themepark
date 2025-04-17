package com.sparta.orderservice.payment.application.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqPaymentTossDto {

    private UUID orderId;
    private Integer amount;
    private String orderName;
    private String paymentKey;

    public static ReqPaymentTossDto of(UUID orderId, Integer amount, String paymentKey) {
        return ReqPaymentTossDto.builder()
                .amount(amount)
                .orderId(orderId)
                .paymentKey(paymentKey)
                .orderName("예시 상품")
                .build();
    }
}

