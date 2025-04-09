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
public class ReqStockRestorePatchDTOApiV1 {

    @NotNull(message = "복구하고자 하는 수량을 입력해주세요")
    private Integer stock;

    @Valid
    @NotNull(message = "상품 정보를 입력해주세요")
    private Product product;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {

        @NotNull(message = "상품의 제한 수량을 입력해주세요")
        private Integer limitQuantity;
    }
}
