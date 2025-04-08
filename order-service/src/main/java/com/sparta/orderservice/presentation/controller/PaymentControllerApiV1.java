package com.sparta.orderservice.presentation.controller;

import com.sparta.orderservice.application.dto.request.ReqPaymentPostDtoApiV1;
import com.sparta.orderservice.application.dto.response.ResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentControllerApiV1 {

    @PostMapping()
    public ResponseEntity<ResDto<Object>> createPayment(
            @RequestBody ReqPaymentPostDtoApiV1 reqPaymentPostDtoApiV1
    ){
        return new ResponseEntity<>(
                ResDto.builder()
                        .code(0)
                        .message("결제를 완료하였습니다.")
                        .build(),
                HttpStatus.CREATED
        );
    }

}
