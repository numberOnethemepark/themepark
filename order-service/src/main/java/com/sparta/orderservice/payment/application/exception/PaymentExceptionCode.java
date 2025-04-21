package com.sparta.orderservice.payment.application.exception;

import com.github.themepark.common.application.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaymentExceptionCode implements ExceptionCode {
    PAYMENT_EXCEPTION_CODE_NOT_FOUND("A101", "해당 결제는 없는 정보입니다.", HttpStatus.NOT_FOUND),
    PAYMENT_FAILURE("A102", "결제를 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
