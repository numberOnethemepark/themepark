package com.sparta.orderservice.presentation.controller;

import com.sparta.orderservice.application.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.application.dto.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.application.dto.response.ResDto;
import com.sparta.orderservice.application.dto.response.ResOrderGetDtoApiV1;
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
                        .message("주문을 생성하였습니다!")
                        .build(),
                HttpStatus.CREATED
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResDto<Object>> updateOrder(
            @PathVariable("id") UUID id,
            @RequestBody ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1) {
        return new ResponseEntity<>(
                ResDto.builder()
                        .code(0) //Ok 코드
                        .message("주문을 수정하였습니다!")
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDto<ResOrderGetDtoApiV1>> getOrder(
            @PathVariable("id") UUID id
    ){
        return new ResponseEntity<>(
                ResDto.<ResOrderGetDtoApiV1>builder()
                        .code(0)
                        .message("주문정보를 조회하였습니다!")
                        .data(ResOrderGetDtoApiV1.of(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDto<Object>> deleteOrder(
            @PathVariable("id") UUID id
    ){
        return new ResponseEntity<>(
                ResDto.builder()
                        .code(0)
                        .message("주문을 삭제하였습니다!")
                        .build(),
                HttpStatus.OK
        );
    }
}

