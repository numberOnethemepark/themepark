package com.sparta.orderservice.order.presentation.controller.v3;

import com.github.themepark.common.application.dto.ResDTO;
import com.sparta.orderservice.order.application.facade.v3.OrderFacadeV3;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.presentation.dto.v3.request.ReqOrderPutDtoApiV3;
import com.sparta.orderservice.order.presentation.dto.v3.request.ReqOrdersPostDtoApiV3;
import com.sparta.orderservice.order.presentation.dto.v3.response.ResOrderGetDtoApiV3;
import com.sparta.orderservice.order.presentation.dto.v3.response.ResOrderPostDtoApiV3;
import com.sparta.orderservice.order.presentation.dto.v3.response.ResOrdersGetByIdDtoApiV3;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/v3/orders")
@RequiredArgsConstructor
public class OrderControllerApiV3 {

    private final OrderFacadeV3 orderFacadeV3;

    @PostMapping
    public ResponseEntity<ResDTO<ResOrderPostDtoApiV3>> postBy(
           @RequestBody ReqOrdersPostDtoApiV3 reqOrdersPostDtoApiV3,
           @RequestHeader("X-User-Id") Long userId
           )  {

        ResOrderPostDtoApiV3 resOrderPostDtoApiV3 = orderFacadeV3.processOrder(userId, reqOrdersPostDtoApiV3);

        return new ResponseEntity<>(
                ResDTO.<ResOrderPostDtoApiV3>builder()
                        .code("0")
                        .message("주문을 생성하였습니다!")
                        .data(resOrderPostDtoApiV3)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> updateBy(
            @PathVariable("id") UUID id,
            @RequestBody ReqOrderPutDtoApiV3 reqOrderPutDtoApiV3
    ) {
        orderFacadeV3.updateBy(reqOrderPutDtoApiV3, id);

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code("0") //Ok 코드
                        .message("주문을 수정하였습니다!")
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResOrderGetDtoApiV3>> getBy(
            @PathVariable("id") UUID id
    ){
        return new ResponseEntity<>(
                ResDTO.<ResOrderGetDtoApiV3>builder()
                        .code("0")
                        .message("주문정보를 조회하였습니다!")
                        .data(ResOrderGetDtoApiV3.of(orderFacadeV3.getOrderBy(id)))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("")
    public ResponseEntity<ResDTO<ResOrdersGetByIdDtoApiV3>> getBy(
            @RequestParam(name = "userId", required = false) Long id,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ){
        Page<OrderEntity> orderPage = orderFacadeV3.getOrdersByUserId(id, page, size);

        return new ResponseEntity<>(
                ResDTO.<ResOrdersGetByIdDtoApiV3>builder()
                        .code("0")
                        .message("주문목록을 조회하였습니다!")
                        .data(ResOrdersGetByIdDtoApiV3.of(orderPage))
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

