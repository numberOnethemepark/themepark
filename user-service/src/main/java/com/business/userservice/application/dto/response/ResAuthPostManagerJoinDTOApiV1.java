package com.business.userservice.application.dto.response;

import com.business.userservice.domain.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResAuthPostManagerJoinDTOApiV1 {

    @JsonProperty
    private User user;

    public static ResAuthPostManagerJoinDTOApiV1 of(UserEntity userEntity) {
        return ResAuthPostManagerJoinDTOApiV1.builder()
            .user(ResAuthPostManagerJoinDTOApiV1.User.from(userEntity))
            .build();
    }

    @Builder
    public static class User {

        @JsonProperty
        private String username;
        @JsonProperty
        private String slackId;

        private static User from(UserEntity userEntity) {
            return User.builder()
                .username(userEntity.getUsername())
                .slackId(userEntity.getSlackId())
                .build();
        }
    }
}
