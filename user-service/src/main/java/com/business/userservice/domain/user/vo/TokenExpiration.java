package com.business.userservice.domain.user.vo;

import lombok.Getter;

@Getter
public enum TokenExpiration {
    REFRESH_TOKEN(1036800, 12),
    ACCESS_TOKEN(86400, 1);

    private final long seconds;
    private final int days;

    TokenExpiration(long seconds, int days) {
        this.seconds = seconds;
        this.days = days;
    }
}
