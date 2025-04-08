package com.business.userservice.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResAuthPostLoginDTOApiV1 {
    @JsonProperty
    private String accessJwt;
    @JsonProperty
    private String refreshJwt;

    public static ResAuthPostLoginDTOApiV1 of(String accessJwt, String refreshJwt) {
        return ResAuthPostLoginDTOApiV1.builder()
            .accessJwt(accessJwt)
            .refreshJwt(refreshJwt)
            .build();
    }
}
