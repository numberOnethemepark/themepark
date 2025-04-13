package com.sparta.orderservice.payment.domain.entity;

import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_payments")
public class Payment extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "payment_id", nullable = false)
    private UUID paymentId;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "payment_number", nullable = true)
    private String paymentNumber;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}
