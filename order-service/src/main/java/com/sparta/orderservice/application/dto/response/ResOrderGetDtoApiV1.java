package com.sparta.orderservice.application.dto.response;

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
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderGetDtoApiV1 {

    @NotNull(message = "주문을 입력해주세요.")
    @Valid
    private Order order;

    public static ResOrderGetDtoApiV1 of(UUID id) {
        return ResOrderGetDtoApiV1.builder()
                .order(Order.from(id))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order{

        private Integer userId;
        private String slackId;
        private String role;
        private Integer amount;
        private String paymentStatus;
        private String paymentCardNumber;

        public static Order from(UUID id){
            return Order.builder()
                    .userId(1)
                    .slackId("slack")
                    .role("admin")
                    .amount(100)
                    .paymentStatus("paid")
                    .paymentCardNumber("123")
                    .build();
        }
    }
}
