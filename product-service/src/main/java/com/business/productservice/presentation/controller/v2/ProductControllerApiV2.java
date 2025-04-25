package com.business.productservice.presentation.controller.v2;

import com.business.productservice.application.dto.v2.request.ReqProductPostDTOApiV2;
import com.business.productservice.application.dto.v2.request.ReqProductPutDTOApiV2;
import com.business.productservice.application.dto.v2.response.*;
import com.business.productservice.application.service.v2.ProductServiceApiV2;
import com.business.productservice.domain.product.entity.ProductEntity;
import com.github.themepark.common.application.aop.annotation.ApiPermission;
import com.github.themepark.common.application.dto.ResDTO;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v2/products")
public class ProductControllerApiV2 {

        private final ProductServiceApiV2 productServiceApiV2;

        @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
        @PostMapping
        public ResponseEntity<ResDTO<ResProductPostDTOApiV2>> postBy(
                @Valid @RequestBody ReqProductPostDTOApiV2 dto
        ){
                ResProductPostDTOApiV2 responseDto = productServiceApiV2.postBy(dto);
                return new ResponseEntity<>(
                        ResDTO.<ResProductPostDTOApiV2>builder()
                                .code("0")
                                .message("상품 등록에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.CREATED
                );
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResDTO<ResProductGetByIdDTOApiV2>> getBy(
                @PathVariable("id") UUID id
        ){
                ResProductGetByIdDTOApiV2 responseDto = productServiceApiV2.getBy(id);

                return new ResponseEntity<>(
                        ResDTO.<ResProductGetByIdDTOApiV2>builder()
                                .code("0")
                                .message("상품 조회에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.OK
                );
        }

        @GetMapping
        public ResponseEntity<ResDTO<ResProductGetDTOApiV2>> getBy(
                @QuerydslPredicate(root = ProductEntity.class) Predicate predicate,
                @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable
        ){
                ResProductGetDTOApiV2 responseDto = productServiceApiV2.getBy(predicate, pageable);

                return new ResponseEntity<>(
                        ResDTO.<ResProductGetDTOApiV2>builder()
                                .code("0")
                                .message("상품 조회에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.OK
                );
        }

        @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
        @PutMapping("/{id}")
        public ResponseEntity<ResDTO<ResProductPutDTOApiV2>> putBy(
                @PathVariable("id") UUID id,
                @Valid @RequestBody ReqProductPutDTOApiV2 dto
        ){
                ResProductPutDTOApiV2 responseDto = productServiceApiV2.putBy(id, dto);
                return new ResponseEntity<>(
                        ResDTO.<ResProductPutDTOApiV2>builder()
                                .code("0")
                                .message("상품 정보 수정에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.OK
                );
        }

        @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
        @DeleteMapping("/{id}")
        public ResponseEntity<ResDTO<Object>> deleteBy(
                @PathVariable("id") UUID id
        ){
                productServiceApiV2.deleteBy(id);
                return new ResponseEntity<>(
                        ResDTO.builder()
                                .code("0")
                                .message("상품 삭제를 성공했습니다.")
                                .build(),
                        HttpStatus.OK
                );
        }

        @PostMapping("/internal/{id}/stocks-decrease")
        public ResponseEntity<ResDTO<Object>> postDecreaseById(
                @PathVariable("id") UUID id
        ){
                productServiceApiV2.postDecreaseById(id);
                return new ResponseEntity<>(
                        ResDTO.builder()
                                .code("0")
                                .message("상품 재고가 차감되었습니다.")
                                .build(),
                        HttpStatus.OK
                );
        }

        @PostMapping("/internal/{id}/stocks-restore")
        public ResponseEntity<ResDTO<Object>> postRestoreById(
                @PathVariable("id") UUID id
        ){
                productServiceApiV2.postRestoreById(id);
                return new ResponseEntity<>(
                        ResDTO.builder()
                                .code("0")
                                .message("상품 재고가 복구되었습니다.")
                                .build(),
                        HttpStatus.OK
                );
        }

        @ApiPermission(roles = {ApiPermission.Role.USER, ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
        @GetMapping("/{id}/stock")
        public ResponseEntity<ResDTO<ResStockGetByIdDTOApiV2>> getStockById(
                @PathVariable("id") UUID id
        ){
                ResStockGetByIdDTOApiV2 responseDto = productServiceApiV2.getStockById(id);
                return new ResponseEntity<>(
                        ResDTO.<ResStockGetByIdDTOApiV2>builder()
                                .code("0")
                                .message("상품 재고 조회에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.OK
                );
        }
}
