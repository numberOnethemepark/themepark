package com.business.productservice.application.dto.v2.response;

import com.business.productservice.domain.product.entity.StockEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResStockGetByIdDTOApiV2 {

    private Stock stock;

    public static ResStockGetByIdDTOApiV2 of(StockEntity stockEntity) {
        return ResStockGetByIdDTOApiV2.builder()
                .stock(Stock.from(stockEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Stock {
        private Integer stock;

        public static Stock from(StockEntity stockEntity) {
            return Stock.builder()
                    .stock(stockEntity.getStock())
                    .build();
        }
    }
}
