package com.business.productservice.application.dto.v1.request;

import com.business.productservice.domain.product.entity.ProductEntity;
import com.business.productservice.domain.product.vo.ProductType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor //테스트 코드를 위해 추가
@AllArgsConstructor //테스트 코드를 위해 추가
public class ReqProductPostDTOApiV1 {

    @Valid
    @NotNull(message = "상품 정보를 입력해주세요.")
    private Product product;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product{

        @NotBlank(message = "상품 이름을 입력해주세요.")
        private String name;

        @NotBlank(message = "상품 설명을 입력해주세요.")
        private String description;

        @NotNull(message = "상품 타입을 입력해주세요.")
        private ProductType productType;

        @NotNull(message = "상품 가격을 입력해주세요.")
        private Integer price;

        @NotNull(message = "상품 제한 수량을 입력해주세요.")
        private Integer limitQuantity;

        @NotNull(message = "사용 가능한 시작일을 입력해주세요.")
        private LocalDateTime eventStartAt;

        @NotNull(message = "사용 가능한 마감일을 입력해주세요.")
        private LocalDateTime eventEndAt;

    }

    public ProductEntity toEntityWithStock() {
        return ProductEntity.createWithStock(
                product.getName(),
                product.getDescription(),
                product.getProductType(),
                product.getPrice(),
                product.getEventStartAt(),
                product.getEventEndAt(),
                product.getLimitQuantity(),
                null // productStatus는 기본값(DRAFT)으로 처리
        );
    }

}
