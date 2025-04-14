package com.sparta.orderservice.order.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import com.github.themepark.common.domain.entity.BaseEntity;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Builder
@Setter
@Table(name = "p_orders")
public class Order extends BaseEntity {
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

    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "payment_status", nullable = false)
    private Integer paymentStatus;

    @Column(name = "payment_id")
    private UUID paymentId;

    public Order() {

    }
}
