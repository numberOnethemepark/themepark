package com.sparta.orderservice.payment.application.dto.request;

import com.sparta.orderservice.order.domain.entity.Order;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqPaymentTossDto {

    private UUID orderId;
    private Integer amount;
    private String orderName;

    public static ReqPaymentTossDto of(UUID orderId, Integer amount) {
        return ReqPaymentTossDto.builder()
                .amount(amount)
                .orderId(orderId)
                .orderName("예시 상품")
                .build();
    }
}

