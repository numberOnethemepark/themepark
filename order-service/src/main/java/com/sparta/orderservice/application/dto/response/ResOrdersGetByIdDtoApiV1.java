package com.sparta.orderservice.application.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResOrdersGetByIdDtoApiV1 {

    public static ResOrdersGetByIdDtoApiV1 of(UUID id, int page, int size) {
        return ResOrdersGetByIdDtoApiV1.builder()
                .orderList(Order.from(id,size,page))
                .build();
    }

    @Valid
    @NotNull(message = "")
    private List<Order> orderList;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order{

        private Orderer orderer;
        private Payments payments;


        public static Order from(UUID id){
            return Order.builder()
                    .orderer(Orderer.from(1,"admin","slackId"))
                    .payments(Payments.from(100, "paid", "123"))
                    .build();
        }

        public static List<Order> from(UUID id, int size, int page){

            List<UUID> idList = List.of(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID());

            return idList.stream()
                    .map(thisId -> Order.from(thisId))
                    .toList();
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

        @Getter
        @Builder
        public static class Payments {

            private Integer amount;
            private String paymentStatus;
            private String paymentCardNumber;

            public static Payments from(Integer amount, String paymentStatus, String paymentCardNumber) {
                return Payments.builder()
                        .amount(amount)
                        .paymentStatus(paymentStatus)
                        .paymentCardNumber(paymentCardNumber)
                        .build();
            }
        }
    }
}
