package com.sparta.orderservice.order.infrastructure.kafka.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqProductKafkaDto {
    private String productId;
    private String orderId;
}
