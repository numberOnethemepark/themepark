package com.business.themeparkservice.waiting.common.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResDTO<T> {
    private Integer code;
    private String message;
    private T data;
}
