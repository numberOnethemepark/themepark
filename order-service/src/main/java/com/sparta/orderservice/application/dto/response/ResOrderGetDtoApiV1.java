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

        private Orderer orderer;
        private Integer amount;
        private String paymentStatus;
        private String paymentCardNumber;

        public static Order from(UUID id){
            return Order.builder()
                    .orderer(Orderer.from(1,"admin","slackId"))
                    .amount(100)
                    .paymentStatus("paid")
                    .paymentCardNumber("123")
                    .build();
        }

        @Getter
        @Builder
        public static class Orderer {

            private Integer userId;
            private String role;
            private String slackId;

            public static Orderer from(Integer userId, String role, String slackId) {
                return Orderer.builder()
                        .userId(userId)
                        .role(role)
                        .slackId(slackId)
                        .build();
            }
        }
    }
}
