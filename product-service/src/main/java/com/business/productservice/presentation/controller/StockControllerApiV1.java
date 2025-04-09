package com.business.productservice.presentation.controller;

import com.business.productservice.application.dto.request.ReqStockDecreasePatchDTOApiV1;
import com.business.productservice.application.dto.request.ReqStockRestorePatchDTOApiV1;
import com.business.productservice.application.dto.response.ResStockDecreasePatchDTOApiV1;
import com.business.productservice.application.dto.response.ResStockRestorePatchDTOApiV1;
import com.business.productservice.common.dto.ResDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/products")
public class StockControllerApiV1 {

    @PatchMapping("/{id}/stocks-decrease")
    public ResponseEntity<ResDTO<ResStockDecreasePatchDTOApiV1>> patchBy(
            @PathVariable UUID id,
            @Valid @RequestBody ReqStockDecreasePatchDTOApiV1 dto
    ){
        return new ResponseEntity<>(
                ResDTO.<ResStockDecreasePatchDTOApiV1>builder()
                        .code(0)
                        .message("상품 재고가 차감되었습니다.")
                        .data(ResStockDecreasePatchDTOApiV1.of())
                        .build(),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}/stocks-restore")
    public ResponseEntity<ResDTO<ResStockRestorePatchDTOApiV1>> patchBy(
            @PathVariable UUID id,
            @Valid @RequestBody ReqStockRestorePatchDTOApiV1 dto
    ){
        return new ResponseEntity<>(
                ResDTO.<ResStockRestorePatchDTOApiV1>builder()
                        .code(0)
                        .message("상품 재고가 복구되었습니다.")
                        .data(ResStockRestorePatchDTOApiV1.of())
                        .build(),
                HttpStatus.OK
        );
    }


}
