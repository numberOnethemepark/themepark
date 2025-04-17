package com.sparta.orderservice.order.presentation.dto.request;

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
public class ReqOrderPutDtoApiV1 {

    @Valid
    @NotNull(message = "주문 정보를 입력해주세요.")
    private Order order;

    @Getter
    @Builder
    public static class Order {

        @NotBlank(message = "슬랙 ID를 입력해주세요.")
        private String slackId;

        @NotBlank(message = "주문상태를 입력해주세요.")
        private Integer paymentStatus;

        private UUID paymentId;
    }

}


