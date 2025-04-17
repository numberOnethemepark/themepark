package com.sparta.orderservice.payment.presentation.dto.request;


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
public class ReqPaymentPostDtoApiV1 {
    @Valid
    @NotNull(message = "결제정보를 입력해주세요.")
    private Payment payment;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Payment {
        @NotBlank(message = "주문아이디를 입력해주세요.")
        public UUID orderId;

        @NotBlank(message = "총금액을 입력해주세요.")
        public Integer amount;

        @NotBlank(message = "결제 key 를 입력해주세요.")
        public String paymentKey;
    }
}
