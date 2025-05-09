package com.business.productservice.application.dto.v2.response;

import com.business.productservice.domain.product.entity.ProductEntity;
import com.business.productservice.domain.product.vo.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResProductPostDTOApiV2 {

    private Product product;

    public static ResProductPostDTOApiV2 of(ProductEntity productEntity){
        return ResProductPostDTOApiV2.builder()
                .product(ResProductPostDTOApiV2.Product.from(productEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product{
        private UUID id;
        private String name;
        private String description;
        private ProductType productType;
        private Integer price;
        private Integer limitQuantity;
        private LocalDateTime eventStartAt;
        private LocalDateTime eventEndAt;

        public static Product from(ProductEntity productEntity){
            return Product.builder()
                    .id(productEntity.getId())
                    .name(productEntity.getName())
                    .description(productEntity.getDescription())
                    .productType(productEntity.getProductType())
                    .price(productEntity.getPrice())
                    .limitQuantity(productEntity.getLimitQuantity())
                    .eventStartAt(productEntity.getEventStartAt())
                    .eventEndAt(productEntity.getEventEndAt())
                    .build();
        }
    }
}
