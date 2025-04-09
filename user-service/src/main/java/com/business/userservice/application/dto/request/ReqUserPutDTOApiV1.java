package com.business.userservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ReqUserPutDTOApiV1 {

    @JsonProperty
    private User user;

    @Builder
    public static class User {
        @JsonProperty
        private String username;
        @JsonProperty
        private String password;
        @JsonProperty
        private String slackId;

//        public void update(UserEntity userEntity) {
//            userEntity.update(username, password, slackId);
//        }
    }
}
