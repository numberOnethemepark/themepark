package com.business.productservice.application.dto.response;

import com.business.productservice.domain.product.entity.StockEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResStockGetByIdDTOApiv1 {

    private Stock stock;

    public static ResStockGetByIdDTOApiv1 of(StockEntity stockEntity) {
        return ResStockGetByIdDTOApiv1.builder()
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
