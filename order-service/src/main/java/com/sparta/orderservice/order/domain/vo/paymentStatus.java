package com.sparta.orderservice.order.domain.vo;

import lombok.Getter;

@Getter
public enum paymentStatus {

    PAID(PaymentStatus.paid),
    NOT_PAID(PaymentStatus.not_paid);

    private final String status;

    paymentStatus(String status) {
        this.status = status;
    }

    public static class PaymentStatus{
        public static final String paid = "PAID";
        public static final String not_paid = "NOT_PAID";
    }

}
