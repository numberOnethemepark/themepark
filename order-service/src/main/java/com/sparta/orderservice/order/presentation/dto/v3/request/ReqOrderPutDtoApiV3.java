package com.sparta.orderservice.order.presentation.dto.v3.request;

import com.sparta.orderservice.payment.domain.vo.PaymentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ReqOrderPutDtoApiV3 {

    @Valid
    @NotNull(message = "주문 정보를 입력해주세요.")
    private Order order;

    @Getter
    @Builder
    public static class Order {

        @NotBlank(message = "주문상태를 입력해주세요.")
        private PaymentStatus paymentStatus;

        private UUID paymentId;
    }

}


