package com.sparta.orderservice.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResDto<T> {
    private Integer code;
    private String message;
    private T data;
}
