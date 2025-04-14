package com.sparta.orderservice.order.application.service;

import com.github.themepark.common.application.dto.ResDTO;
import com.sparta.orderservice.order.application.client.PaymentServiceClient;
import com.sparta.orderservice.order.application.dto.request.ReqPaymentPostDtoApiV1;
import com.sparta.orderservice.order.application.dto.response.ResPaymentPostDtoApiV1;
import com.sparta.orderservice.order.application.usecase.OrderUseCase;
import com.sparta.orderservice.order.domain.entity.Order;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

    private final OrderRepository orderRepository;
    private final PaymentServiceClient paymentServiceClient;

    @Override
    public void createOrder(ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1){

        // 주문객체 만들기
        Order order = Order.builder()
                .userId(1L)
                .orderQuantity(reqOrdersPostDtoApiV1.getOrder().getOrderQuantity())
                .amount(reqOrdersPostDtoApiV1.getOrder().getAmount())
                .productId(reqOrdersPostDtoApiV1.getOrder().getProductId())
                .slackId(reqOrdersPostDtoApiV1.getOrder().getSlackId())
                .paymentStatus(0) // 결제 미완상태 -> 0
                .build();


        // 생성된 주문객체로 결제
        ReqPaymentPostDtoApiV1 reqPaymentPostDtoApiV1 = ReqPaymentPostDtoApiV1.builder()
                .payment(ReqPaymentPostDtoApiV1.Payment.builder()
                        .orderId(order.getOrderId())
                        .amount(order.getAmount())
                        .build())
                .build();

        ResponseEntity<ResDTO<ResPaymentPostDtoApiV1>> resPaymentPostDtoApiV1 =  paymentServiceClient.PostPayment(reqPaymentPostDtoApiV1);

        // 카드번호와, 결제상태를 결제완료로 변경
        order.setPaymentStatus(1);
        order.setPaymentId(Objects.requireNonNull(resPaymentPostDtoApiV1.getBody()).getData().getPayment().getPaymentId());

        orderRepository.save(order);
    }
}
