package com.sparta.orderservice.order.application.service.v1;

import com.github.themepark.common.application.dto.ResDTO;
import com.sparta.orderservice.order.application.dto.reponse.ResProductGetByIdDTOApi;
import com.sparta.orderservice.order.application.usecase.v1.OrderUseCaseV1;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.infrastructure.feign.ProductFeignClientApi;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.v1.response.ResOrderPostDtoApiV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;


@Service
public class OrderServiceV1 implements OrderUseCaseV1 {

    private final OrderRepository orderRepository;
    private final ProductFeignClientApi productFeignClientApi;

    public OrderServiceV1(
            OrderRepository orderRepository,
            ProductFeignClientApi productFeignClientApi
    ) {
        this.orderRepository = orderRepository;
        this.productFeignClientApi = productFeignClientApi;
    }

    @Override
    public ResOrderPostDtoApiV1 processOrder(Long userId, ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1){
        ResDTO<ResProductGetByIdDTOApi> resProductGetByIdDTOApiV1ResDTO = productFeignClientApi.getBy(reqOrdersPostDtoApiV1.getOrder().getProductId());

        if (Objects.equals(resProductGetByIdDTOApiV1ResDTO.getData().getProduct().getProductType(), "EVENT")) {
            productFeignClientApi.getStockById(reqOrdersPostDtoApiV1.getOrder().getProductId());
            productFeignClientApi.postDecreaseById(reqOrdersPostDtoApiV1.getOrder().getProductId());
        }

        OrderEntity orderEntity = OrderEntity.createOrder(reqOrdersPostDtoApiV1.getOrder().getProductId(),
                reqOrdersPostDtoApiV1.getOrder().getAmount(),
                reqOrdersPostDtoApiV1.getOrder().getSlackId(),
                userId);
        orderRepository.save(orderEntity);

        return ResOrderPostDtoApiV1.of(orderEntity);
    }


    @Override
    public void updateBy(ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1, UUID orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId);
        OrderEntity.updateOrder(orderEntity, reqOrderPutDtoApiV1.getOrder().getPaymentId(), reqOrderPutDtoApiV1.getOrder().getPaymentStatus());
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
