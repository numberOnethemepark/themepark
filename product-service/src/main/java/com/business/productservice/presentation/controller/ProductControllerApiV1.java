package com.business.productservice.presentation.controller;

import com.business.productservice.application.dto.request.ReqProductPostDTOApiV1;
import com.business.productservice.application.dto.response.ResProductPostDTOApiV1;
import com.business.productservice.common.dto.ResDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/product")
public class ProductControllerApiV1 {

        @PostMapping
        public ResponseEntity<ResDTO<ResProductPostDTOApiV1>> postBy(
                @Valid @RequestBody ReqProductPostDTOApiV1 dto
        ){
                ResProductPostDTOApiV1.Product product = ResProductPostDTOApiV1.Product.builder()
                        .name("20% 할인권")
                        .price(30000)
                        .build();

                ResProductPostDTOApiV1 resProductPostDTOApiV1 = ResProductPostDTOApiV1.builder()
                        .product(product)
                        .build();

                return new ResponseEntity<>(
                        ResDTO.<ResProductPostDTOApiV1>builder()
                                .code(0)
                                .message("상품 등록에 성공했습니다.")
                                .data(resProductPostDTOApiV1)
                                .build(),
                        HttpStatus.OK
                );
        }
}
