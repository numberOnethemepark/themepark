package com.sparta.orderservice.presentation.controller;

import com.sparta.orderservice.application.dto.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.application.dto.response.ResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("v1/orders")
@RequiredArgsConstructor
public class OrderControllerApiV1 {

    @PostMapping
    public ResponseEntity<ResDto<Object>> createOrder(
            @Valid @RequestBody ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1) {
        System.out.println(reqOrdersPostDtoApiV1.toString());
        return new ResponseEntity<>(
                ResDto.builder()
                        .code(0) //Ok 코드
                        .message("상품을 생성하였습니다!")
                        .build(),
                HttpStatus.OK
        );
    }

}
