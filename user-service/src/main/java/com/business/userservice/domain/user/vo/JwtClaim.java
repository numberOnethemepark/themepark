package com.business.userservice.domain.user.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtClaim {
    CATEGORY("category"),
    USER_ID("userId"),
    ROLE("role"),
    SLACK_ID("slackId");

    private final String name;
}
