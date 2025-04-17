package com.sparta.orderservice.order.domain.entity;

import com.sparta.orderservice.order.presentation.dto.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.request.ReqOrdersPostDtoApiV1;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import com.github.themepark.common.domain.entity.BaseEntity;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "p_orders")
public class OrderEntity extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "slack_id", nullable = false)
    private String slackId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "payment_status", nullable = false)
    private Integer paymentStatus;

    @Column(name = "payment_id")
    private UUID paymentId;

    public static OrderEntity createOrder(ReqOrdersPostDtoApiV1 reqOrdersPostDtoApiV1){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.userId = 1L;
        orderEntity.productId = reqOrdersPostDtoApiV1.getOrder().getProductId();
        orderEntity.amount = reqOrdersPostDtoApiV1.getOrder().getAmount();
        orderEntity.slackId = reqOrdersPostDtoApiV1.getOrder().getSlackId();
        orderEntity.paymentStatus = 0;
        orderEntity.paymentId = null;

        return orderEntity;
    }

    public static void updateOrder(OrderEntity orderEntity, ReqOrderPutDtoApiV1 reqOrderPutDtoApiV1) {
        if(reqOrderPutDtoApiV1.getOrder().getPaymentStatus() != null) {orderEntity.paymentStatus = reqOrderPutDtoApiV1.getOrder().getPaymentStatus();}
        if(reqOrderPutDtoApiV1.getOrder().getSlackId() != null) {orderEntity.slackId = reqOrderPutDtoApiV1.getOrder().getSlackId();}
        if(reqOrderPutDtoApiV1.getOrder().getPaymentId() != null) {orderEntity.paymentId = reqOrderPutDtoApiV1.getOrder().getPaymentId();}
    }

}
