package com.business.userservice.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResAuthPostLoginDTOApiV1 {
    private String accessJwt;
    private String refreshJwt;

    public static ResAuthPostLoginDTOApiV1 of(String accessJwt, String refreshJwt) {
        return ResAuthPostLoginDTOApiV1.builder()
            .accessJwt(accessJwt)
            .refreshJwt(refreshJwt)
            .build();
    }
}
