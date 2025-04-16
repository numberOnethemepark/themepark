package com.business.themeparkservice.themepark.application.exception;

import com.github.themepark.common.application.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ThemeparkExceptionCode implements ExceptionCode {
    THEMEPARK_NOT_FOUND("T101","존재하지않는 테마파크입니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
