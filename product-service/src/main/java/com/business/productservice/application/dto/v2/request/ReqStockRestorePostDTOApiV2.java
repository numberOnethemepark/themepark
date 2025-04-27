package com.business.productservice.application.dto.v2.request;

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
public class ReqStockRestorePostDTOApiV2 {

    @Valid
    @NotNull(message = "재고 정보를 입력해주세요")
    private Stock stock;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Stock {

        @NotNull(message = "상품 재고 복구 수량을 입력해주세요.")
        private Integer stockRestoreAmount;
    }
}
