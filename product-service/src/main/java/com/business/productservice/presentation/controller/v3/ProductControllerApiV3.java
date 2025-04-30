package com.business.productservice.presentation.controller.v3;

import com.business.productservice.application.dto.v3.request.ReqProductPostDTOApiV3;
import com.business.productservice.application.dto.v3.request.ReqProductPutDTOApiV3;
import com.business.productservice.application.dto.v3.response.*;
import com.business.productservice.application.service.v3.ProductServiceApiV3;
import com.business.productservice.domain.product.entity.ProductEntity;
import com.business.productservice.infrastructure.kafka.TestKafkaProducer;
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
@RequestMapping("/v3/products")
public class ProductControllerApiV3 {

        private final ProductServiceApiV3 productServiceApiV3;
        private final TestKafkaProducer testKafkaProducer;

        @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
        @PostMapping
        public ResponseEntity<ResDTO<ResProductPostDTOApiV3>> postBy(
                @Valid @RequestBody ReqProductPostDTOApiV3 dto
        ){
                ResProductPostDTOApiV3 responseDto = productServiceApiV3.postBy(dto);
                return new ResponseEntity<>(
                        ResDTO.<ResProductPostDTOApiV3>builder()
                                .code("0")
                                .message("상품 등록에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.CREATED
                );
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResDTO<ResProductGetByIdDTOApiV3>> getBy(
                @PathVariable("id") UUID id
        ){
                ResProductGetByIdDTOApiV3 responseDto = productServiceApiV3.getBy(id);

                return new ResponseEntity<>(
                        ResDTO.<ResProductGetByIdDTOApiV3>builder()
                                .code("0")
                                .message("상품 조회에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.OK
                );
        }

        @GetMapping
        public ResponseEntity<ResDTO<ResProductGetDTOApiV3>> getBy(
                @QuerydslPredicate(root = ProductEntity.class) Predicate predicate,
                @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable
        ){
                ResProductGetDTOApiV3 responseDto = productServiceApiV3.getBy(predicate, pageable);

                return new ResponseEntity<>(
                        ResDTO.<ResProductGetDTOApiV3>builder()
                                .code("0")
                                .message("상품 조회에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.OK
                );
        }

        @ApiPermission(roles = {ApiPermission.Role.MASTER, ApiPermission.Role.MANAGER})
        @PutMapping("/{id}")
        public ResponseEntity<ResDTO<ResProductPutDTOApiV3>> putBy(
                @PathVariable("id") UUID id,
                @Valid @RequestBody ReqProductPutDTOApiV3 dto
        ){
                ResProductPutDTOApiV3 responseDto = productServiceApiV3.putBy(id, dto);
                return new ResponseEntity<>(
                        ResDTO.<ResProductPutDTOApiV3>builder()
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
                productServiceApiV3.deleteBy(id);
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
                productServiceApiV3.postDecreaseById(id);
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
                productServiceApiV3.postRestoreById(id);
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
        public ResponseEntity<ResDTO<ResStockGetByIdDTOApiV3>> getStockById(
                @PathVariable("id") UUID id
        ){
                ResStockGetByIdDTOApiV3 responseDto = productServiceApiV3.getStockById(id);
                return new ResponseEntity<>(
                        ResDTO.<ResStockGetByIdDTOApiV3>builder()
                                .code("0")
                                .message("상품 재고 조회에 성공했습니다.")
                                .data(responseDto)
                                .build(),
                        HttpStatus.OK
                );
        }

        //카프카 테스트 용 (삭제 예정)
        @PostMapping("/send-stock-decrease")
        public String sendStockDecrease(@RequestParam("productId") UUID productId) {
                testKafkaProducer.sendStockDecreaseTest(productId);
                return "Stock decrease test message sent!";
        }
}
