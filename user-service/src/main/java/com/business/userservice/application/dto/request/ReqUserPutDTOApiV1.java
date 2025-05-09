package com.business.userservice.application.dto.request;

import com.business.userservice.domain.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReqUserPutDTOApiV1 {

    private User user;

    @Builder
    public static class User {
        @JsonProperty
        private String username;
        @JsonProperty
        private String password;
        @JsonProperty
        private String slackId;

        public UserEntity update(UserEntity userEntity) {
            return userEntity.update(username, password, slackId);
        }
    }
}
