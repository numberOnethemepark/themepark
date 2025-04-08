package com.business.userservice.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResAuthPostGuestLoginDTOApiV1 {

    @JsonProperty
    private String accessJwt;

    public static ResAuthPostGuestLoginDTOApiV1 of(String accessJwt) {
        return ResAuthPostGuestLoginDTOApiV1.builder()
            .accessJwt(accessJwt)
            .build();
    }
}
