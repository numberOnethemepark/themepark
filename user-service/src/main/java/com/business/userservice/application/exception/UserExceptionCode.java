package com.business.userservice.application.exception;

import com.github.themepark.common.application.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionCode implements ExceptionCode {
    USER_NOT_FOUND("U101", "존재하지 않는 회원 아이디입니다.", HttpStatus.NOT_FOUND),
    PASSWORD_NOT_MATCH("U102", "비밀번호가 일치하지 않습니다." , HttpStatus.UNAUTHORIZED),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
