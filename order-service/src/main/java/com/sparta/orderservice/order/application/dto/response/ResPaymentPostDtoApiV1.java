package com.sparta.orderservice.order.application.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResPaymentPostDtoApiV1 {

    @Valid
    @NotNull
    private Payment payment;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Payment {

        @NotBlank(message = "결제 ID를 입력해주세요.")
        private UUID paymentId;

        @NotBlank(message = "카드번호를 입력해주세요.")
        private String cardNumber;

    }
}
