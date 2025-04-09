package com.business.productservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResProductPostDTOApiV1 {

    private Product product;

    public static ResProductPostDTOApiV1 of(){
        Product product = Product.builder()
                .name("20% 할인권")
                .price(30000)
                .build();

        return ResProductPostDTOApiV1.builder()
                .product(product)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product{
        private String name;
        private Integer price;
    }
}
