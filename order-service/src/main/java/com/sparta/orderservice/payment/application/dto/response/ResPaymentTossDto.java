package com.sparta.orderservice.payment.application.dto.response;

import jakarta.validation.Valid;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPaymentTossDto {

    private String paymentKey;
    private UUID orderId;
    private String status;
    private Card card;
    private Failure failure;

    @Getter
    @Builder
    public static class Card{
        private Integer amount;
        private String number;
    }

    @Getter
    @Builder
    public static class Failure{
        private String code;
        private String message;
    }
}
