package com.business.themeparkservice.waiting.infastructure.domain.vo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TargetType {
    USER_DM("사용자 개인 DM"),
    ADMIN_CHANNEL("관리자 채널");

    private final String description;
}
