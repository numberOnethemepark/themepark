package com.business.productservice.presentation.controller;

import com.business.productservice.application.dto.request.ReqProductPostDTOApiV1;
import com.business.productservice.application.dto.request.ReqProductPutDTOApiV1;
import com.business.productservice.application.dto.request.ReqStockDecreasePatchDTOApiV1;
import com.business.productservice.application.dto.request.ReqStockRestorePatchDTOApiV1;
import com.business.productservice.application.dto.response.*;
import com.business.productservice.common.dto.ResDTO;
import com.business.productservice.domain.product.entity.ProductEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/products")
public class ProductControllerApiV1 {

        @PostMapping
        public ResponseEntity<ResDTO<ResProductPostDTOApiV1>> postBy(
                @Valid @RequestBody ReqProductPostDTOApiV1 dto
        ){
                return new ResponseEntity<>(
                        ResDTO.<ResProductPostDTOApiV1>builder()
                                .code(0)
                                .message("상품 등록에 성공했습니다.")
                                .data(ResProductPostDTOApiV1.of())
                                .build(),
                        HttpStatus.OK
                );
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResDTO<ResProductGetByIdDTOApiV1>> getById(
                @PathVariable UUID id
        ){
                return new ResponseEntity<>(
                        ResDTO.<ResProductGetByIdDTOApiV1>builder()
                                .code(0)
                                .message("상품 조회에 성공했습니다.")
                                .data(ResProductGetByIdDTOApiV1.of())
                                .build(),
                        HttpStatus.OK
                );
        }

        @GetMapping
        public ResponseEntity<ResDTO<ResProductGetDTOApiV1>> getBy(
                @RequestParam(required = false) String searchValue,
                @PageableDefault(sort="id", direction = Sort.Direction.ASC) Pageable pageable,
                Long id //TODO 권한 처리용 (추후 변경 예정)
        ){
                List<ProductEntity> tempProducts = List.of(
                        new ProductEntity(),
                        new ProductEntity()
                );

                Page<ProductEntity> tempProductPage = new PageImpl<>(tempProducts, pageable, tempProducts.size());

                ResProductGetDTOApiV1 tempResDto = ResProductGetDTOApiV1.of(tempProductPage);

                return ResponseEntity.ok(
                        ResDTO.<ResProductGetDTOApiV1>builder()
                                .code(0)
                                .message("상품 조회에 성공했습니다.")
                                .data(tempResDto)
                                .build()
                );
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResDTO<Object>> putById(
                @PathVariable UUID id,
                @Valid @RequestBody ReqProductPutDTOApiV1 dto
        ){
                return new ResponseEntity<>(
                        ResDTO.builder()
                                .code(0)
                                .message("상품 정보 수정에 성공했습니다.")
                                .build(),
                        HttpStatus.OK
                );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResDTO<Object>> deleteById(
                @PathVariable UUID id
        ){
                return new ResponseEntity<>(
                        ResDTO.builder()
                                .code(0)
                                .message("상품 삭제를 성공했습니다.")
                                .build(),
                        HttpStatus.OK
                );
        }

        @PostMapping("/{id}/stocks-decrease")
        public ResponseEntity<ResDTO<Object>> patchBy(
                @PathVariable UUID id,
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

        @PostMapping("/{id}/stocks-restore")
        public ResponseEntity<ResDTO<Object>> patchBy(
                @PathVariable UUID id,
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
