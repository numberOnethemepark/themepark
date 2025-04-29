package com.sparta.orderservice.order.domain.entity;

import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrderPutDtoApiV1;
import com.sparta.orderservice.order.presentation.dto.v1.request.ReqOrdersPostDtoApiV1;
import com.sparta.orderservice.payment.domain.vo.PaymentStatus;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_id")
    private UUID paymentId;

    public static OrderEntity createOrder(UUID productId, Integer amount, String slackId, Long userId){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.userId = userId;
        orderEntity.productId = productId;
        orderEntity.amount = amount;
        orderEntity.slackId = slackId;
        orderEntity.paymentStatus = PaymentStatus.NOT_PAID;
        orderEntity.paymentId = null;

        return orderEntity;
    }

    public static void updateOrder(OrderEntity orderEntity, UUID paymentId, PaymentStatus paymentStatus) {
        orderEntity.paymentId = paymentId;
        orderEntity.paymentStatus = paymentStatus;
    }

}
