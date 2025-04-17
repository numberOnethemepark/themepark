package com.business.gatewayservice.application.exception;

import com.github.themepark.common.application.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GatewayExceptionCode implements ExceptionCode {
    EXPIRED_JWT("G101", "만료된 JWT Token 입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_SIGNATURE("G102", "유효하지 않은 JWT 서명 입니다.", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_JWT("G103", "지원되지 않는 JWT Token 입니다.", HttpStatus.BAD_REQUEST),
    EMPTY_CLAIMS("G104", "잘못된 JWT Token 입니다.", HttpStatus.BAD_REQUEST),

    AUTH_HEADER_MISSING("G201", "Authorization 헤더가 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),
    BEARER_TOKEN_NOT_FOUND("G202", "Bearer 타입의 토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
