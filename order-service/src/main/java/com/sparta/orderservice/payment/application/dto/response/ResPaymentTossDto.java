package com.sparta.orderservice.payment.application.dto.response;

import jakarta.validation.Valid;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPaymentTossDto {

    @Valid
    private Payment payment;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Payment{
        private String paymentKey;
        private UUID orderId;
        private String status;
        private Card card;

        @Getter
        @Builder
        public static class Card{
            private Number amount;
            private String number;
        }
    }
}
