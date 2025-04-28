package com.sparta.orderservice.order.presentation.dto.v3.response;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.payment.domain.vo.PaymentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ResOrdersGetByIdDtoApiV3 {

    @Valid
    @NotNull(message = "주문 리스트는 비어 있을 수 없습니다.")
    private List<Order.OrderItem> orders;

    public static ResOrdersGetByIdDtoApiV3 of(Page<OrderEntity> orderPage) {
        return ResOrdersGetByIdDtoApiV3.builder()
                .orders(Order.from(orderPage))
                .build();
    }

    @Getter
    @Builder
    public static class Order {

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Orderer {

            private Long userId;
            private String role;
            private String slackId;

            public static Orderer from(Long userId, String role, String slackId) {
                return Orderer.builder()
                        .userId(userId)
                        .role(role)
                        .slackId(slackId)
                        .build();
            }
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Payments {

            private Integer amount;
            private PaymentStatus paymentStatus;
            private String paymentCardNumber;

            public static Payments from(Integer amount, PaymentStatus paymentStatus) {
                return Payments.builder()
                        .amount(amount)
                        .paymentStatus(paymentStatus)
                        .build();
            }
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class OrderItem {
            private Orderer orderer;
            private Payments payments;

            public static OrderItem of(Orderer orderer, Payments payments) {
                return OrderItem.builder()
                        .orderer(orderer)
                        .payments(payments)
                        .build();
            }
        }

        // Page<OrderEntity> → List<OrderItem> 변환
        public static List<OrderItem> from(Page<OrderEntity> orderPage) {
            return orderPage.getContent().stream()
                    .map(orderEntity -> {
                        Orderer orderer = Orderer.from(
                                orderEntity.getUserId(),
                                "admin",
                                orderEntity.getSlackId()
                        );

                        Payments payments = Payments.from(
                                orderEntity.getAmount(),
                                orderEntity.getPaymentStatus()
                        );

                        return OrderItem.of(orderer, payments);
                    })
                    .collect(Collectors.toList());
        }
    }
}
