package com.sparta.orderservice.order.application.service.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.themepark.common.application.dto.ResDTO;
import com.sparta.orderservice.order.application.dto.reponse.ResProductGetByIdDTOApi;
import com.sparta.orderservice.order.application.usecase.v2.OrderUseCaseV2;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.infrastructure.feign.ProductFeignClientApi;
import com.sparta.orderservice.order.presentation.dto.v2.request.ReqOrderPutDtoApiV2;
import com.sparta.orderservice.order.presentation.dto.v2.request.ReqOrdersPostDtoApiV2;
import com.sparta.orderservice.order.presentation.dto.v2.response.ResOrderPostDtoApiV2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;


@Service
public class OrderServiceV2 implements OrderUseCaseV2 {

    private final OrderRepository orderRepository;
    private final ProductFeignClientApi productFeignClientApi;
    private final RedisTemplate<String, ResProductGetByIdDTOApi> productRedisTemplate;
    private final ObjectMapper objectMapper;

    public OrderServiceV2(
            OrderRepository orderRepository,
            ProductFeignClientApi productFeignClientApi,
            @Qualifier("ProductRedisTemplate") RedisTemplate<String, ResProductGetByIdDTOApi> productRedisTemplate,
            ObjectMapper objectMapper
    ) {
        this.orderRepository = orderRepository;
        this.productFeignClientApi = productFeignClientApi;
        this.productRedisTemplate = productRedisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResOrderPostDtoApiV2 processOrder(Long userId, ReqOrdersPostDtoApiV2 reqOrdersPostDtoApiV2){

        String productId = reqOrdersPostDtoApiV2.getOrder().getProductId().toString();
        String redisProductKey = "stock : " + productId;

        Object raw = productRedisTemplate.opsForValue().get(redisProductKey);
        ResProductGetByIdDTOApi product = objectMapper.convertValue(raw, ResProductGetByIdDTOApi.class);

        if (product == null) {
            ResDTO<ResProductGetByIdDTOApi> resProductGetByIdDTOApiV2ResDTO = productFeignClientApi.getBy(reqOrdersPostDtoApiV2.getOrder().getProductId());
            productRedisTemplate.opsForValue().set(redisProductKey, resProductGetByIdDTOApiV2ResDTO.getData());

            if (Objects.equals(resProductGetByIdDTOApiV2ResDTO.getData().getProduct().getProductType(), "EVENT")) {
                productFeignClientApi.getStockById(reqOrdersPostDtoApiV2.getOrder().getProductId());
                productFeignClientApi.postDecreaseById(reqOrdersPostDtoApiV2.getOrder().getProductId());
            }
        }
        else {
            if (Objects.equals(product.getProduct().getProductType(), "EVENT")) {
                productFeignClientApi.getStockById(reqOrdersPostDtoApiV2.getOrder().getProductId());
                productFeignClientApi.postDecreaseById(reqOrdersPostDtoApiV2.getOrder().getProductId());
            }
        }

        OrderEntity orderEntity = OrderEntity.createOrder(reqOrdersPostDtoApiV2.getOrder().getProductId(),
                    reqOrdersPostDtoApiV2.getOrder().getAmount(),
                    reqOrdersPostDtoApiV2.getOrder().getSlackId(),
                    userId);
        orderRepository.save(orderEntity);

        return ResOrderPostDtoApiV2.of(orderEntity);
    }


    @Override
    public void updateBy(ReqOrderPutDtoApiV2 reqOrderPutDtoApiV2, UUID orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId);
        OrderEntity.updateOrder(orderEntity, reqOrderPutDtoApiV2.getOrder().getPaymentId(), reqOrderPutDtoApiV2.getOrder().getPaymentStatus());
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
