package com.sparta.orderservice.order.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.orderservice.order.application.dto.reponse.ResProductGetByIdDTOApiV1;
import com.sparta.orderservice.order.application.facade.OrderFacade;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.infrastructure.feign.ProductFeignClientApiV1;
import com.sparta.orderservice.order.presentation.dto.response.ResOrderPostDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.response.ResOrdersGetByIdDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
import com.github.themepark.common.application.dto.ResDTO;
import com.sparta.orderservice.order.presentation.dto.response.ResOrderGetDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderControllerApiV1 {

    private final OrderFacade orderFacade;
    private final ProductFeignClientApiV1 productFeignClientApiV1;
    private final RedisTemplate<String, ResProductGetByIdDTOApiV1> redisTemplate;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<ResDTO<ResOrderPostDtoApiV1>> postBy(
           @RequestBody ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1,
           @RequestHeader("X-User-Id") Long userId
           )  {

        // Redis 에서 상품 정보 조회
        String productId = reqOrdersPostDtoApiV1.getOrder().getProductId().toString();
        Object raw = redisTemplate.opsForValue().get(productId);
        ResProductGetByIdDTOApiV1 product = objectMapper.convertValue(raw, ResProductGetByIdDTOApiV1.class);

        // redis 에 해당 productId를 갖고있는 데이터가 존재하지 않는다면 redis 에 저장
        if(product == null){
            ResDTO<ResProductGetByIdDTOApiV1> resProductGetByIdDTOApiV1ResDTO = productFeignClientApiV1.getBy(reqOrdersPostDtoApiV1.getOrder().getProductId());
            product = resProductGetByIdDTOApiV1ResDTO.getData();
            redisTemplate.opsForValue().set(productId, product, 10, TimeUnit.MINUTES);

            if (Objects.equals(resProductGetByIdDTOApiV1ResDTO.getData().getProduct().getProductType(), "EVENT")) {
                // 재고조회 -> product service 에서 재고가 없을시 error -> 재고차감
                productFeignClientApiV1.getStockById(reqOrdersPostDtoApiV1.getOrder().getProductId());
                productFeignClientApiV1.postDecreaseById(reqOrdersPostDtoApiV1.getOrder().getProductId());
            }
        }
        else{
            if(Objects.equals(product.getProduct().getProductType(), "EVENT")){
                productFeignClientApiV1.getStockById(reqOrdersPostDtoApiV1.getOrder().getProductId());
                productFeignClientApiV1.postDecreaseById(reqOrdersPostDtoApiV1.getOrder().getProductId());
            }
        }

        // 주문시작
        OrderEntity orderEntity = orderFacade.postBy(reqOrdersPostDtoApiV1, userId);

        return new ResponseEntity<>(
                ResDTO.<ResOrderPostDtoApiV1>builder()
                        .code("0")
                        .message("주문을 생성하였습니다!")
                        .data(ResOrderPostDtoApiV1.of(orderEntity))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> updateBy(
            @PathVariable("id") UUID id,
            @RequestBody ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1
    ) {
        orderFacade.updateBy(reqOrderPutDtoApiV1, id);

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
                        .data(ResOrderGetDtoApiV1.of(orderFacade.getOrderBy(id)))
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
        Page<OrderEntity> orderPage = orderFacade.getOrdersByUserId(id, page, size);

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

