package com.business.userservice.application.dto.response;

import com.business.userservice.domain.user.entity.UserEntity;
import com.business.userservice.domain.user.vo.RoleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResUserGetByIdDTOApiV1 {

    @JsonProperty
    private User user;

    public static ResUserGetByIdDTOApiV1 of(UserEntity userEntity) {
        return ResUserGetByIdDTOApiV1.builder()
            .user(User.from(userEntity))
            .build();
    }

    @Builder
    public static class User {

        @JsonProperty
        private String username;
        @JsonProperty
        private String slackId;
        @JsonProperty
        private RoleType role;

        private static User from(UserEntity userEntity) {
            return User.builder()
                .username(userEntity.getUsername())
                .slackId(userEntity.getSlackId())
                .role(userEntity.getRole())
                .build();
        }
    }
}
