package com.business.themeparkservice.hashtag.application.exception;

import com.github.themepark.common.application.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HashtagExceptionCode implements ExceptionCode {
    HASHTAG_NOT_FOUND("H101","존재하지않는 해시태그입니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
