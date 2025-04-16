package com.business.productservice.application.dto.request;

import com.business.productservice.domain.product.vo.ProductStatus;
import com.business.productservice.domain.product.vo.ProductType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
@Slf4j
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqProductPutDTOApiV1 {
    @Valid
    @NotNull(message = "상품 변경 정보를 입력해주세요.")
    private Product product;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product{

        private String name;
        private String description;
        private ProductType productType;
        private Integer price;
        private Integer limitQuantity;
        private LocalDateTime eventStartAt;
        private LocalDateTime eventEndAt;
        private ProductStatus productStatus;

    }
}
