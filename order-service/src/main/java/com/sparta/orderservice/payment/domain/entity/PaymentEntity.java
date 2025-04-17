package com.sparta.orderservice.payment.domain.entity;

import com.github.themepark.common.domain.entity.BaseEntity;
import com.sparta.orderservice.payment.application.dto.response.ResPaymentTossDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_payments")
public class PaymentEntity extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID paymentId;

    @Column(name = "payment_key", nullable = false)
    private String paymentKey;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "card_number", nullable = true)
    private String cardNumber;

    @Column(name = "amount", nullable = false)
    private Integer amount;

   public static PaymentEntity createPayment(ResPaymentTossDto tossRes){
       PaymentEntity paymentEntity = new PaymentEntity();
       paymentEntity.paymentKey = tossRes.getPaymentKey();
       paymentEntity.orderId = tossRes.getOrderId();
       paymentEntity.paymentStatus = tossRes.getStatus();
       paymentEntity.cardNumber = tossRes.getCard().getNumber();
       paymentEntity.amount = tossRes.getCard().getAmount();

       return paymentEntity;
   }
}
