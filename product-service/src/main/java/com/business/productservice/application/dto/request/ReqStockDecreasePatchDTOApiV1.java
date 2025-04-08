package com.business.productservice.application.dto.request;

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
public class ReqStockDecreasePatchDTOApiV1 {

    @Valid
    @NotNull(message = "주문하고자 하는 상품 수를 입력해주세요")
    private Stock stock;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Stock {
        private Integer stock;
    }
}
