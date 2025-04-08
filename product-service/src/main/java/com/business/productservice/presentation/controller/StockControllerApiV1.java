package com.business.productservice.presentation.controller;

import com.business.productservice.application.dto.request.ReqStockDecreasePatchDTOApiV1;
import com.business.productservice.application.dto.request.ReqStockRestorePatchDTOApiV1;
import com.business.productservice.common.dto.ResDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/products")
public class StockControllerApiV1 {

    @PatchMapping("/{id}/stocks-decrease")
    public ResponseEntity<ResDTO<Object>> patchBy(
            @Valid @RequestBody ReqStockDecreasePatchDTOApiV1 dto
    ){
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("상품 재고가 차감되었습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}/stocks-restore")
    public ResponseEntity<ResDTO<Object>> patchBy(
            @Valid @RequestBody ReqStockRestorePatchDTOApiV1 dto
    ){
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("상품 재고가 복구되었습니다.")
                        .build(),
                HttpStatus.OK
        );
    }


}
