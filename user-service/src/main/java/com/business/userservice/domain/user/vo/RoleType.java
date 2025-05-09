package com.business.userservice.domain.user.vo;

import lombok.Getter;

@Getter
public enum RoleType {
    MASTER(Authority.MASTER),
    MANAGER(Authority.MANAGER),
    USER(Authority.USER);

    private final String authority;

    RoleType(String authority) {
        this.authority = authority;
    }

    public static class Authority {

        public static final String MASTER = "ROLE_MASTER";
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String USER = "ROLE_USER";
    }
}
