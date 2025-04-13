package com.sparta.orderservice.order.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResDto<T> {
    private Integer code;
    private String message;
    private T data;
}
