package com.sparta.orderservice.order.application.dto.reponse;

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

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product{
        private String name;
        private String description;
        private String productType;
        private Integer price;
        private Integer limitQuantity;
        private LocalDateTime eventStartAt;
        private LocalDateTime eventEndAt;
        private String productStatus;
    }
}
