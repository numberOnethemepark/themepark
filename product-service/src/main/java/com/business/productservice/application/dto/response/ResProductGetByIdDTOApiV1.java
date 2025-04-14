package com.business.productservice.application.dto.response;

import com.business.productservice.domain.product.vo.ProductStatus;
import com.business.productservice.domain.product.vo.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResProductGetByIdDTOApiV1 {

    private Product product;

    public static ResProductGetByIdDTOApiV1 of(){
        return ResProductGetByIdDTOApiV1.builder()
                .product(Product.from())
                .build();
    }

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

        public static Product from(){
            return Product.builder()
                    .name("20% 할인권")
                    .description("상품 설명입니다.")
                    .productType(ProductType.EVENT)
                    .price(30000)
                    .limitQuantity(100)
                    .eventStartAt(LocalDateTime.now())
                    .eventEndAt(LocalDateTime.MAX)
                    .productStatus(ProductStatus.OPEN)
                    .build();
        }
    }
}
