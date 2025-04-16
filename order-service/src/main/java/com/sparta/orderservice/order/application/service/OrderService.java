package com.sparta.orderservice.order.application.service;

import com.sparta.orderservice.order.application.usecase.OrderUseCase;
import com.sparta.orderservice.order.domain.entity.Order;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public Order createOrder(ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1){
        // 주문객체 만들기
        Order order = Order.builder()
                .userId(1L)
                .amount(reqOrdersPostDtoApiV1.getOrder().getAmount())
                .productId(reqOrdersPostDtoApiV1.getOrder().getProductId())
                .slackId(reqOrdersPostDtoApiV1.getOrder().getSlackId())
                .paymentStatus(0) // 결제 미완상태 -> 0
                .build();

        orderRepository.save(order);

        return order;
    }
}
