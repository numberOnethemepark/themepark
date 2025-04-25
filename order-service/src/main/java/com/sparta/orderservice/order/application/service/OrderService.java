package com.sparta.orderservice.order.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.themepark.common.application.dto.ResDTO;
import com.github.themepark.common.application.exception.CustomException;
import com.sparta.orderservice.order.application.dto.reponse.ResProductGetByIdDTOApiV1;
import com.sparta.orderservice.order.application.exception.OrderExceptionCode;
import com.sparta.orderservice.order.application.facade.OrderFacade;
import com.sparta.orderservice.order.application.usecase.OrderUseCase;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.infrastructure.feign.ProductFeignClientApiV1;
import com.sparta.orderservice.order.infrastructure.kafka.service.KafkaService;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.response.ResOrderPostDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class OrderService implements OrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductFeignClientApiV1 productFeignClientApiV1;
    private final RedisTemplate<String, ResProductGetByIdDTOApiV1> productRedisTemplate;
    private final RedisTemplate<String, String> stockRedisTemplate;
    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;

    public OrderService(
            OrderRepository orderRepository,
            ProductFeignClientApiV1 productFeignClientApiV1,
            @Qualifier("ProductRedisTemplate") RedisTemplate<String, ResProductGetByIdDTOApiV1> productRedisTemplate,
            @Qualifier("stockRedisTemplate") RedisTemplate<String, String> stockRedisTemplate,
            KafkaService kafkaService,
            ObjectMapper objectMapper
    ) {
        this.orderRepository = orderRepository;
        this.productFeignClientApiV1 = productFeignClientApiV1;
        this.productRedisTemplate = productRedisTemplate;
        this.stockRedisTemplate = stockRedisTemplate;
        this.kafkaService = kafkaService;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResOrderPostDtoApiV1 processOrder(Long userId, ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1){
        long start = System.currentTimeMillis();

        //Redis 에서 상품 정보 조회
        String productId = reqOrdersPostDtoApiV1.getOrder().getProductId().toString();
        String redisProductKey = "stock : " + productId;
        String redisStockKey = "key : " + productId;

        Object raw = productRedisTemplate.opsForValue().get(redisProductKey);
        ResProductGetByIdDTOApiV1 product = objectMapper.convertValue(raw, ResProductGetByIdDTOApiV1.class);

        // 해당 productId 정보가 존재하지 않는다면
        if(product == null){
            ResDTO<ResProductGetByIdDTOApiV1> resProductGetByIdDTOApiV1ResDTO = productFeignClientApiV1.getBy(reqOrdersPostDtoApiV1.getOrder().getProductId());
            productRedisTemplate.opsForValue().set(redisProductKey, resProductGetByIdDTOApiV1ResDTO.getData());

            if (Objects.equals(resProductGetByIdDTOApiV1ResDTO.getData().getProduct().getProductType(), "EVENT")) {
                Integer quantity = resProductGetByIdDTOApiV1ResDTO.getData().getProduct().getLimitQuantity();
                stockRedisTemplate.opsForValue().set(redisStockKey, String.valueOf(quantity), 10, TimeUnit.MINUTES);
                // Redis 로 재고관리
                Long remain = stockRedisTemplate.opsForValue().decrement(redisStockKey);
                if(remain == null || remain < 0){
                    stockRedisTemplate.opsForValue().increment(redisStockKey);
                    throw new CustomException(OrderExceptionCode.ORDER_OUT_OF_STOCK);
                }
                // kafka 메세지
                kafkaService.sendOrderAsync("stock-decrease-topic", productId);
            }
        }
        else{
            if(Objects.equals(product.getProduct().getProductType(), "EVENT")){
                // Redis 로 재고관리
                Long remain = stockRedisTemplate.opsForValue().decrement(redisStockKey);
                if(remain == null || remain < 0){
                    stockRedisTemplate.opsForValue().increment(redisStockKey);
                    throw new CustomException(OrderExceptionCode.ORDER_OUT_OF_STOCK);
                }
                // kafka 메세지
                kafkaService.sendOrderAsync("stock-decrease-topic", productId);
            }
        }

        long redisTime = System.currentTimeMillis();

        // 주문시작
        OrderEntity orderEntity = OrderEntity.createOrder(reqOrdersPostDtoApiV1, userId);
        orderRepository.save(orderEntity);

        long dbTime = System.currentTimeMillis();

        System.out.println("Redis 소요: " + (redisTime - start) + "ms");
        System.out.println("DB 소요: " + (dbTime - redisTime) + "ms");

        return ResOrderPostDtoApiV1.of(orderEntity);
    }


    @Override
    public void updateBy(ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1, UUID orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId);
        OrderEntity.updateOrder(orderEntity, reqOrderPutDtoApiV1);
    }

    @Override
    public OrderEntity getOrderBy(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return orderRepository.findByUserId(userId, pageable);
    }

}
