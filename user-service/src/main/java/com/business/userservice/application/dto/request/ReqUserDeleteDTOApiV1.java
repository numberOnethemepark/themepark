package com.business.userservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ReqUserDeleteDTOApiV1 {

    @JsonProperty
    private User user;

    @Builder
    public static class User {

        @JsonProperty
        private String password;
    }
}
