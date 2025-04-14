package com.sparta.orderservice.order.presentation.controller;

import com.sparta.orderservice.order.application.facade.OrderFacade;
import com.sparta.orderservice.order.presentation.dto.response.ResOrdersGetByIdDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
import com.github.themepark.common.application.dto.ResDTO;
import com.sparta.orderservice.order.presentation.dto.response.ResOrderGetDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderControllerApiV1 {

    private final OrderFacade orderFacade;

    @PostMapping
    public ResponseEntity<ResDTO<Object>> createOrder(
           @RequestBody ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1) {

        orderFacade.createOrder(reqOrdersPostDtoApiV1);

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0) //Ok 코드
                        .message("주문을 생성하였습니다!")
                        .build(),
                HttpStatus.CREATED
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> updateBy(
            @PathVariable("id") UUID id,
            @RequestBody ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1
    ) {
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0) //Ok 코드
                        .message("주문을 수정하였습니다!")
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResOrderGetDtoApiV1>> getBy(
            @PathVariable("id") UUID id
    ){
        return new ResponseEntity<>(
                ResDTO.<ResOrderGetDtoApiV1>builder()
                        .code(0)
                        .message("주문정보를 조회하였습니다!")
                        .data(ResOrderGetDtoApiV1.of(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("")
    public ResponseEntity<ResDTO<ResOrdersGetByIdDtoApiV1>> getBy(
            @RequestParam(name = "userId", required = false) UUID id,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ){
        return new ResponseEntity<>(
                ResDTO.<ResOrdersGetByIdDtoApiV1>builder()
                        .code(0)
                        .message("주문목록을 조회하였습니다!")
                        .data(ResOrdersGetByIdDtoApiV1.of(id, page, size))
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> deleteBy(
            @PathVariable("id") UUID id
    ){
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("주문을 삭제하였습니다!")
                        .build(),
                HttpStatus.OK
        );
    }
}

