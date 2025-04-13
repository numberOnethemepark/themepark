package com.sparta.orderservice.payment.presentation.controller;

import com.sparta.orderservice.payment.application.dto.request.ReqPaymentPostDtoApiV1;
import com.sparta.orderservice.order.application.dto.response.ResDto;
import com.sparta.orderservice.payment.application.dto.response.ResPaymentGetDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentControllerApiV1 {

    @PostMapping()
    public ResponseEntity<ResDto<Object>> postBy(
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

    @PutMapping("/{id}")
    public ResponseEntity<ResDto<Object>> updateBy(
            @RequestBody ReqPaymentPostDtoApiV1 reqPaymentPostDtoApiV1,
            @PathVariable("id") UUID id
    ){
        return new ResponseEntity<>(
                ResDto.builder()
                        .code(0)
                        .message("결제가 수정되었습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDto<ResPaymentGetDtoApiV1>> getPayment(
            @PathVariable("id") UUID id
    ){
        return new ResponseEntity<>(
                ResDto.<ResPaymentGetDtoApiV1>builder()
                        .code(0)
                        .message("결제정보를 조회하였습니다.")
                        .data(ResPaymentGetDtoApiV1.of(id))
                        .build(),
                HttpStatus.OK
        );
    }

}
