package com.business.productservice.presentation.controller;

import com.business.productservice.application.dto.request.ReqProductPostDTOApiV1;
import com.business.productservice.application.dto.request.ReqProductPutDTOApiV1;
import com.business.productservice.application.dto.request.ReqStockDecreasePostDTOApiV1;
import com.business.productservice.application.dto.request.ReqStockRestorePostDTOApiV1;
import com.business.productservice.application.dto.response.*;
import com.business.productservice.application.service.ProductServiceApiV1;
import com.business.productservice.domain.product.entity.ProductEntity;
import com.github.themepark.common.application.dto.ResDTO;
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

        private final ProductServiceApiV1 productServiceApiV1;

        @PostMapping
        public ResponseEntity<ResDTO<ResProductPostDTOApiV1>> postBy(
                @Valid @RequestBody ReqProductPostDTOApiV1 dto
        ){
                ResProductPostDTOApiV1 responseDto = productServiceApiV1.postBy(dto);
                return new ResponseEntity<>(
                        ResDTO.<ResProductPostDTOApiV1>builder()
                                .code(0)
                                .message("상품 등록에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.CREATED
                );
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResDTO<ResProductGetByIdDTOApiV1>> getBy(
                @PathVariable("id") UUID id
        ){
                ResProductGetByIdDTOApiV1 responseDto = productServiceApiV1.getBy(id);

                return new ResponseEntity<>(
                        ResDTO.<ResProductGetByIdDTOApiV1>builder()
                                .code(0)
                                .message("상품 조회에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.OK
                );
        }

        @GetMapping
        public ResponseEntity<ResDTO<ResProductGetDTOApiV1>> getBy(
                @RequestParam(name = "searchValue", required = false) String searchValue,
                @PageableDefault(sort="id", direction = Sort.Direction.ASC) Pageable pageable
//                Long id //TODO 권한 처리용 (추후 변경 예정)
        ){
                List<ProductEntity> tempProducts = List.of(
                        new ProductEntity(),
                        new ProductEntity()
                );

                Page<ProductEntity> tempProductPage = new PageImpl<>(tempProducts, pageable, tempProducts.size());

                ResProductGetDTOApiV1 tempResDto = ResProductGetDTOApiV1.of(tempProductPage);

                return new ResponseEntity<>(
                        ResDTO.<ResProductGetDTOApiV1>builder()
                                .code(0)
                                .message("상품 조회에 성공했습니다.")
                                .data(tempResDto)
                                .build(),
                        HttpStatus.OK
                );
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResDTO<ResProductPutDTOApiV1>> putBy(
                @PathVariable("id") UUID id,
                @Valid @RequestBody ReqProductPutDTOApiV1 dto
        ){
                ResProductPutDTOApiV1 responseDto = productServiceApiV1.putBy(id, dto);
                return new ResponseEntity<>(
                        ResDTO.<ResProductPutDTOApiV1>builder()
                                .code(0)
                                .message("상품 정보 수정에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.OK
                );
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResDTO<Object>> deleteById(
                @PathVariable("id") UUID id
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
        public ResponseEntity<ResDTO<Object>> postDecreaseById(
                @PathVariable("id") UUID id,
                @Valid @RequestBody ReqStockDecreasePostDTOApiV1 dto
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
        public ResponseEntity<ResDTO<Object>> postRestoreById(
                @PathVariable("id") UUID id,
                @Valid @RequestBody ReqStockRestorePostDTOApiV1 dto
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
