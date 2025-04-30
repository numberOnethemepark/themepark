package com.business.productservice.infrastructure.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqStockDecreaseSuccessDTOApiV3 {
    private String orderId;
    private String productId;
}
