package com.business.userservice.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResUserGetByIdDTOApiV1 {

    @JsonProperty
    private String username;
    @JsonProperty
    private String slackId;


    public static ResUserGetByIdDTOApiV1 of(String username, String slackId) {
        return ResUserGetByIdDTOApiV1.builder()
            .username(username)
            .slackId(slackId)
            .build();
    }
}
