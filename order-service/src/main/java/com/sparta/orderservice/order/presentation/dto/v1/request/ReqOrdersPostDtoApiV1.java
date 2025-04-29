package com.sparta.orderservice.order.presentation.dto.v1.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class ReqOrdersPostDtoApiV1 {

    @Valid
    @NotNull(message = "주문 정보를 입력해주세요.")
    private Order order;

    @Getter
    @Builder
    public static class Order {

        @NotBlank(message = "슬랙 정보를 입력해주세요.")
        private String slackId;

        @NotBlank(message = "상품의 정보를 입력해주세요.")
        private UUID productId;

        @NotBlank(message = "주문의 금액을 입력해주세요.")
        private Integer amount;
    }
}

