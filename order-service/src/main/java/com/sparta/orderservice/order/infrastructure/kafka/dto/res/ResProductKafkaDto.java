package com.sparta.orderservice.order.infrastructure.kafka.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResProductKafkaDto {
    private String productId;
    private String orderId;
    private String reason;
}
