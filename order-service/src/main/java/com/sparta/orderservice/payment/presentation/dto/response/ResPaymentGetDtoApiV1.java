package com.sparta.orderservice.payment.presentation.dto.response;

import com.sparta.orderservice.payment.domain.entity.PaymentEntity;
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

    public static ResPaymentGetDtoApiV1 of(PaymentEntity paymentEntity) {
        return ResPaymentGetDtoApiV1
                .builder()
                .payment(Payment.from(paymentEntity))
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

        public static Payment from(PaymentEntity paymentEntity){
            return Payment.builder()
                    .orderId(paymentEntity.getOrderId())
                    .amount(paymentEntity.getAmount())
                    .paymentStatus(paymentEntity.getPaymentStatus())
                    .build();
        }
    }

}
