package com.business.productservice.application.dto.response;

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
public class ResProductGetDTOApiV1 {

    private ProductPage productPage;

    public static ResProductGetDTOApiV1 of(Page<ProductEntity> productEntityPage){
        return ResProductGetDTOApiV1.builder()
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
                        .id(UUID.fromString("0d47686e-c73c-4d70-80ae-ef51ea4bfed3"))
                        .name("20% 할인 이벤트")
                        .description("설명입니다.")
                        .productType(ProductType.EVENT)
                        .price(30000)
                        .limitQuantity(100)
                        .eventStartAt(LocalDateTime.now())
                        .eventEndAt(LocalDateTime.now())
                        .productStatus(ProductStatus.OPEN)
                        .build();
            }

        }
    }
}
