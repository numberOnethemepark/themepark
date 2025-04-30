package com.business.productservice.infrastructure.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqStockDecreaseDTOApiV3 {
    private String orderId;
    private String productId;

    public UUID getProductId() {
        if (productId == null) {
            throw new IllegalArgumentException("productId는 null일 수 없습니다.");
        }
        return UUID.fromString(productId);
    }
}
