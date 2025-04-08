package com.sparta.orderservice.presentation.controller;

import com.sparta.orderservice.application.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.application.dto.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.application.dto.response.ResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@RestController
@RequestMapping("v1/orders")
@RequiredArgsConstructor
public class OrderControllerApiV1 {

    @PostMapping
    public ResponseEntity<ResDto<Object>> createOrder(
            @Valid @RequestBody ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1) {

        return new ResponseEntity<>(
                ResDto.builder()
                        .code(0) //Ok 코드
                        .message("상품을 생성하였습니다!")
                        .build(),
                HttpStatus.OK
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResDto<Object>> updateOrder(
            @PathVariable("id") @UUID String id,
            @RequestBody ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1) {
        return new ResponseEntity<>(
            ResDto.builder()
                    .code(0) //Ok 코드
                    .message("상품을 수정하였습니다!")
                    .build(),
            HttpStatus.OK
        );
    }
}

