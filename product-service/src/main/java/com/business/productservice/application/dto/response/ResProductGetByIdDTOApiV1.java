package com.business.productservice.application.dto.response;

import com.business.productservice.domain.product.entity.ProductEntity;
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

    public static ResProductGetByIdDTOApiV1 of(ProductEntity productEntity){
        return ResProductGetByIdDTOApiV1.builder()
                .product(Product.from(productEntity))
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

        public static Product from(ProductEntity productEntity){
            return Product.builder()
                    .name(productEntity.getName())
                    .description(productEntity.getDescription())
                    .productType(productEntity.getProductType())
                    .price(productEntity.getPrice())
                    .limitQuantity(productEntity.getLimitQuantity())
                    .eventStartAt(productEntity.getEventStartAt())
                    .eventEndAt(productEntity.getEventEndAt())
                    .productStatus(productEntity.getProductStatus())
                    .build();
        }
    }
}
