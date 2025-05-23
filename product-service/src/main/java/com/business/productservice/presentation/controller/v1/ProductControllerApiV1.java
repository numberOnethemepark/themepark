package com.business.productservice.presentation.controller.v1;

import com.business.productservice.application.dto.v1.request.ReqProductPostDTOApiV1;
import com.business.productservice.application.dto.v1.request.ReqProductPutDTOApiV1;
import com.business.productservice.application.dto.v1.response.*;
import com.business.productservice.application.service.v1.ProductServiceApiV1;
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
@RequestMapping("/v1/products")
public class ProductControllerApiV1 {

        private final ProductServiceApiV1 productServiceApiV1;

        @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
        @PostMapping
        public ResponseEntity<ResDTO<ResProductPostDTOApiV1>> postBy(
                @Valid @RequestBody ReqProductPostDTOApiV1 dto
        ){
                ResProductPostDTOApiV1 responseDto = productServiceApiV1.postBy(dto);
                return new ResponseEntity<>(
                        ResDTO.<ResProductPostDTOApiV1>builder()
                                .code("0")
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
                                .code("0")
                                .message("상품 조회에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.OK
                );
        }

        @GetMapping
        public ResponseEntity<ResDTO<ResProductGetDTOApiV1>> getBy(
                @QuerydslPredicate(root = ProductEntity.class) Predicate predicate,
                @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable
        ){
                ResProductGetDTOApiV1 responseDto = productServiceApiV1.getBy(predicate, pageable);

                return new ResponseEntity<>(
                        ResDTO.<ResProductGetDTOApiV1>builder()
                                .code("0")
                                .message("상품 조회에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.OK
                );
        }

        @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
        @PutMapping("/{id}")
        public ResponseEntity<ResDTO<ResProductPutDTOApiV1>> putBy(
                @PathVariable("id") UUID id,
                @Valid @RequestBody ReqProductPutDTOApiV1 dto
        ){
                ResProductPutDTOApiV1 responseDto = productServiceApiV1.putBy(id, dto);
                return new ResponseEntity<>(
                        ResDTO.<ResProductPutDTOApiV1>builder()
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
                productServiceApiV1.deleteBy(id);
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
                productServiceApiV1.postDecreaseById(id);
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
                productServiceApiV1.postRestoreById(id);
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
        public ResponseEntity<ResDTO<ResStockGetByIdDTOApiV1>> getStockById(
                @PathVariable("id") UUID id
        ){
                ResStockGetByIdDTOApiV1 responseDto = productServiceApiV1.getStockById(id);
                return new ResponseEntity<>(
                        ResDTO.<ResStockGetByIdDTOApiV1>builder()
                                .code("0")
                                .message("상품 재고 조회에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.OK
                );
        }
}
