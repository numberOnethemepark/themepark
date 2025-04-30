package com.sparta.orderservice.payment.domain.vo;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    PAID("PAID"),
    NOT_PAID("NOT_PAID"),
    WAITING("WAITING");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }
}