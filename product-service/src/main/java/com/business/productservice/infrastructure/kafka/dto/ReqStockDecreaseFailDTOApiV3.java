package com.business.productservice.infrastructure.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqStockDecreaseFailDTOApiV3 {
    private String orderId;
    private String productId;
    private String reason;
}
