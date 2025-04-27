package com.business.productservice.application.dto.v2.response;

import com.business.productservice.domain.product.entity.ProductEntity;
import com.business.productservice.domain.product.vo.ProductStatus;
import com.business.productservice.domain.product.vo.ProductType;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResProductGetDTOApiV2 {

    private ProductPage productPage;

    public static ResProductGetDTOApiV2 of(Page<ProductEntity> productEntityPage){
        return ResProductGetDTOApiV2.builder()
                .productPage(new ProductPage(productEntityPage))
                .build();
    }

    @Getter
    @ToString
    public static class ProductPage extends PagedModel<ProductPage.Product> {

        public ProductPage(Page<ProductEntity> npostScrapEntityPage) {
            super(
                    new PageImpl<>(
                            Product.from(npostScrapEntityPage.getContent()),
                            npostScrapEntityPage.getPageable(),
                            npostScrapEntityPage.getTotalElements()
                    )
            );
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Product {
            private UUID id;
            private String name;
            private String description;
            private ProductType productType;
            private Integer price;
            private Integer limitQuantity;
            private LocalDateTime eventStartAt;
            private LocalDateTime eventEndAt;
            private ProductStatus productStatus;

            public static List<Product> from(List<ProductEntity> productEntityList) {
                return productEntityList.stream()
                        .map(Product::from)
                        .toList();
            }
            public static Product from(ProductEntity entity) {
                return Product.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .productType(entity.getProductType())
                        .price(entity.getPrice())
                        .limitQuantity(entity.getLimitQuantity())
                        .eventStartAt(entity.getEventStartAt())
                        .eventEndAt(entity.getEventEndAt())
                        .productStatus(entity.getProductStatus())
                        .build();
            }

        }
    }
}
