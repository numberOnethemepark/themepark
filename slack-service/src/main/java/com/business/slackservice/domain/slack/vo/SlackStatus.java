package com.business.slackservice.domain.slack.vo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SlackStatus {
    SENT("전송 성공"),
    FAILED("전송 실패");

    private final String description;
}
