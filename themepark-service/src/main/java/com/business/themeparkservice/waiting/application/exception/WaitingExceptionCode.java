package com.business.themeparkservice.waiting.application.exception;

import com.github.themepark.common.application.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum WaitingExceptionCode implements ExceptionCode {
    WAITING_NOT_FOUND("W101","존재하지않는 대기열입니다.", HttpStatus.NOT_FOUND),
    WAITING_DUPLICATE("W102","이미 대기중 입니다.", HttpStatus.CONFLICT),
    WAITING_LOCK_FAILED("W103","요청량이 많습니다 잠시후에 다시 시도해주세요.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
