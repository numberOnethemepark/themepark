package com.sparta.orderservice.payment.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReqPaymentPutDtoApiV1 {

    @NotNull(message = "결제 정보를 입력해주세요.")
    @Valid
    private Payment payment;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Payment{
        @NotBlank(message = "결제 상태를 입력해주세요.")
        private String paymentStatus;
    }

}
