package com.sparta.orderservice.order.presentation.dto.v2.response;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.payment.domain.vo.PaymentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderGetDtoApiV2 {

    @NotNull(message = "주문을 입력해주세요.")
    @Valid
    private Order order;

    public static ResOrderGetDtoApiV2 of(OrderEntity orderEntity) {
        return ResOrderGetDtoApiV2.builder()
                .order(Order.from(orderEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order{

        private Orderer orderer;
        private Payments payments;

        public static Order from(OrderEntity orderEntity){
            return Order.builder()
                    .orderer(Orderer.from(1,orderEntity.getSlackId()))
                    .payments(Payments.from(orderEntity.getAmount(), orderEntity.getPaymentStatus(), "1234"))
                    .build();
        }

        @Getter
        @Builder
        public static class Orderer {

            private Integer userId;
            private String slackId;

            public static Orderer from(Integer userId, String slackId) {
                return Orderer.builder()
                        .userId(userId)
                        .slackId(slackId)
                        .build();
            }
        }

        @Getter
        @Builder
        public static class Payments {

            private Integer amount;
            private PaymentStatus paymentStatus;
            private String paymentCardNumber;

            public static Payments from(Integer amount, PaymentStatus paymentStatus, String paymentCardNumber) {
                return Payments.builder()
                        .amount(amount)
                        .paymentStatus(paymentStatus)
                        .paymentCardNumber(paymentCardNumber)
                        .build();
            }
        }
    }
}
