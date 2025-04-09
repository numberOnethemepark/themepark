package com.business.userservice.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResUserGetByIdDTOApiV1 {
    @JsonProperty
    private User user;

    public static ResUserGetByIdDTOApiV1 of(String username, String slackId) {
        return ResUserGetByIdDTOApiV1.builder()
            .user(User.from(username, slackId))
            .build();
    }

    @Builder
    public static class User {
        private String username;
        private String slackId;

        private static User from(String username, String slackId) {
            return User.builder()
                .username(username)
                .slackId(slackId)
                .build();
        }
    }
}
