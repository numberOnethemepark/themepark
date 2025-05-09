package com.sparta.orderservice.order.application.exception;

import com.github.themepark.common.application.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderExceptionCode implements ExceptionCode {
    ORDER_NOT_FOUND("O101", "해당 주문은 없는 정보입니다.", HttpStatus.NOT_FOUND),
    ORDER_CANCEL("O102", "주문에 실패하였습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
