package com.sparta.orderservice.application.dto.request;

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

        @NotBlank(message = "주문제품의 수량을 입력해주세요.")
        private Integer orderQuantity;

        @NotBlank(message = "상품의 정보를 입력해주세요.")
        private UUID productId;


    }
}

