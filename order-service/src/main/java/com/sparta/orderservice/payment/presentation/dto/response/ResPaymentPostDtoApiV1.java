package com.sparta.orderservice.payment.presentation.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResPaymentPostDtoApiV1 {

    public static ResPaymentPostDtoApiV1 of(){
        return new ResPaymentPostDtoApiV1();
    }

    @Valid
    @NotNull
    private Payment payment;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Payment {
        @NotBlank(message = "카드번호를 입력해주세요.")
        private String cardNumber;
    }
}
