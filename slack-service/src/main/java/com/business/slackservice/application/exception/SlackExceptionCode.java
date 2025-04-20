package com.business.slackservice.application.exception;

import com.github.themepark.common.application.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Slack Event Type - S1XX
 * Slack Template - S2XX
 * Slack - S3XX
 */

@Getter
@RequiredArgsConstructor
public enum SlackExceptionCode implements ExceptionCode {
    SLACK_EVENT_TYPE_NOT_FOUND("S101", "슬랙 이벤트 타입을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SLACK_EVENT_TYPE_NAME_DUPLICATED("S102", "이미 존재하는 슬랙 이벤트 타입 이름입니다.", HttpStatus.BAD_REQUEST),

    SLACK_TEMPLATE_NOT_FOUND("S201", "슬랙 양식을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SLACK_TEMPLATE_DUPLICATED("S202", "이미 존재하는 양식이 있습니다.", HttpStatus.BAD_REQUEST),

    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
