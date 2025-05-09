package com.business.userservice.application.exception;

import com.github.themepark.common.application.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionCode implements ExceptionCode {
    MISSING_USERNAME_OR_PASSWORD("J101","아이디와 비밀번호를 입력해주세요.", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST_BODY("J102", "요청 바디를 JSON으로 파싱하지 못했습니다.", HttpStatus.BAD_REQUEST),
    RESPONSE_WRITE_FAIL("J103", "응답을 작성하는데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTHENTICATION_FAILED("J104", "인증에 실패했습니다. 아이디 또는 비밀번호를 확인해주세요.", HttpStatus.UNAUTHORIZED),
    DUPLICATE_USERNAME("J105", "이미 존재하는 사용자입니다.", HttpStatus.CONFLICT),
    DUPLICATE_SLACK_ID("J106", "이미 존재하는 Slack ID입니다.", HttpStatus.CONFLICT),

    REFRESH_TOKEN_NOT_FOUND("J201", "존재하지 않는 refresh token 입니다.", HttpStatus.NOT_FOUND),
    REFRESH_TOKEN_USER_MISMATCH("J202", "만료된 access token의 user와 요청한 refresh token의 user가 동일하지 않습니다.", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED("J203", "만료된 refresh token 입니다.", HttpStatus.UNAUTHORIZED),

    BLOCKED_USER("J301", "차단된 사용자입니다.", HttpStatus.UNAUTHORIZED),
    ;


    private final String code;
    private final String message;
    private final HttpStatus status;

}
