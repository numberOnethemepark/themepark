package com.sparta.orderservice.application.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPaymentGetDtoApiV1 {

    @NotNull(message = "결제 정보를 입력해주세요.")
    @Valid
    private Payment payment;

    public static ResPaymentGetDtoApiV1 of(UUID id) {
        return ResPaymentGetDtoApiV1
                .builder()
                .payment(Payment.from(id))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payment {
        public UUID orderId;
        public Integer amount;
        public String paymentStatus;

        public static Payment from(UUID id){
            return Payment.builder()
                    .orderId(id)
                    .amount(100)
                    .paymentStatus("결제 완료")
                    .build();
        }
    }

}
