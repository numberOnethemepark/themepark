package com.sparta.orderservice.order.presentation.dto.v3.response;

import com.sparta.orderservice.order.domain.entity.OrderEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ResOrderPostDtoApiV3 {

    private Order order;

    public static ResOrderPostDtoApiV3 of(OrderEntity orderEntity) {
        return ResOrderPostDtoApiV3.builder()
                .order(Order.from(orderEntity))
                .build();
    };

    @Getter
    @Builder
    public static class Order {
        private String slackId;
        private UUID productId;
        private Integer amount;
        private UUID orderId;

        public static Order from(OrderEntity orderEntity) {
            return Order.builder()
                    .slackId(orderEntity.getSlackId())
                    .productId(orderEntity.getProductId())
                    .amount(orderEntity.getAmount())
                    .orderId(orderEntity.getOrderId())
                    .build();
        }
    }
}
