package com.sparta.orderservice.order.presentation.controller.v1;

import com.sparta.orderservice.order.application.facade.v1.OrderFacadeV1;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.v1.response.ResOrderPostDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.v1.response.ResOrdersGetByIdDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrdersPostDtoApiV1;
import com.github.themepark.common.application.dto.ResDTO;
import com.sparta.orderservice.order.presentation.dto.v1.response.ResOrderGetDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.UUID;


@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderControllerApiV1 {

    private final OrderFacadeV1 orderFacadeV1;

    @PostMapping
    public ResponseEntity<ResDTO<ResOrderPostDtoApiV1>> postBy(
           @RequestBody ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1,
           @RequestHeader("X-User-Id") Long userId
           )  {

        ResOrderPostDtoApiV1 resOrderPostDtoApiV1 = orderFacadeV1.processOrder(userId, reqOrdersPostDtoApiV1);

        return new ResponseEntity<>(
                ResDTO.<ResOrderPostDtoApiV1>builder()
                        .code("0")
                        .message("주문을 생성하였습니다!")
                        .data(resOrderPostDtoApiV1)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> updateBy(
            @PathVariable("id") UUID id,
            @RequestBody ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1
    ) {
        orderFacadeV1.updateBy(reqOrderPutDtoApiV1, id);

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code("0") //Ok 코드
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
                        .code("0")
                        .message("주문정보를 조회하였습니다!")
                        .data(ResOrderGetDtoApiV1.of(orderFacadeV1.getOrderBy(id)))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("")
    public ResponseEntity<ResDTO<ResOrdersGetByIdDtoApiV1>> getBy(
            @RequestParam(name = "userId", required = false) Long id,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ){
        Page<OrderEntity> orderPage = orderFacadeV1.getOrdersByUserId(id, page, size);

        return new ResponseEntity<>(
                ResDTO.<ResOrdersGetByIdDtoApiV1>builder()
                        .code("0")
                        .message("주문목록을 조회하였습니다!")
                        .data(ResOrdersGetByIdDtoApiV1.of(orderPage))
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
                        .code("0")
                        .message("주문을 삭제하였습니다!")
                        .build(),
                HttpStatus.OK
        );
    }
}

