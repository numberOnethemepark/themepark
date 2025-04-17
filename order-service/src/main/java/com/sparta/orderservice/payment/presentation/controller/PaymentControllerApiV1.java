package com.sparta.orderservice.payment.presentation.controller;

import com.sparta.orderservice.payment.application.service.PaymentService;
import com.sparta.orderservice.payment.domain.entity.PaymentEntity;
import com.sparta.orderservice.payment.presentation.dto.request.ReqPaymentPostDtoApiV1;
import com.github.themepark.common.application.dto.ResDTO;
import com.sparta.orderservice.payment.presentation.dto.request.ReqPaymentPutDtoApiV1;
import com.sparta.orderservice.payment.presentation.dto.response.ResPaymentGetDtoApiV1;
import com.sparta.orderservice.payment.presentation.dto.response.ResPaymentPostDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentControllerApiV1 {

    private final PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<ResDTO<ResPaymentPostDtoApiV1>> postBy(
            @RequestBody ReqPaymentPostDtoApiV1 reqPaymentPostDtoApiV1
    ){
        paymentService.createPayment(reqPaymentPostDtoApiV1);

        return new ResponseEntity<>(
                ResDTO.<ResPaymentPostDtoApiV1>builder()
                        .code(0)
                        .message("결제를 완료하였습니다.")
                        .data(ResPaymentPostDtoApiV1.of())
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> updateBy(
            @RequestBody ReqPaymentPutDtoApiV1 reqPaymentPutDtoApiV1,
            @PathVariable("id") UUID id
    ){
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("결제가 수정되었습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResPaymentGetDtoApiV1>> getBy(
            @PathVariable("id") UUID id
    ){

        return new ResponseEntity<>(
                ResDTO.<ResPaymentGetDtoApiV1>builder()
                        .code(0)
                        .message("결제정보를 조회하였습니다.")
                        .data(ResPaymentGetDtoApiV1.of(paymentService.getBy(id)))
                        .build(),
                HttpStatus.OK
        );
    }
}
