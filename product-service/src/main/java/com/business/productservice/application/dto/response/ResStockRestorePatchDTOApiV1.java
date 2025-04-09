package com.business.productservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResStockRestorePatchDTOApiV1 {

    private boolean success;

    public static ResStockRestorePatchDTOApiV1 of() {
        return ResStockRestorePatchDTOApiV1.builder()
                .success(true)
                .build();
    }
}
