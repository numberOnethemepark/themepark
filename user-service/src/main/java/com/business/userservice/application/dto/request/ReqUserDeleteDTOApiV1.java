package com.business.userservice.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReqUserDeleteDTOApiV1 {
    private User user;

    @Getter
    @Builder
    public static class User {
        private String password;
    }
}
