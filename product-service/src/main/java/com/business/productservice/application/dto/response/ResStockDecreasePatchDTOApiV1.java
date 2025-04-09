package com.business.productservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResStockDecreasePatchDTOApiV1 {

    private boolean success;

    public static ResStockDecreasePatchDTOApiV1 of() {
        return ResStockDecreasePatchDTOApiV1.builder()
                .success(true)
                .build();
    }
}
