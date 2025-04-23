package com.business.userservice.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResAuthPutDTOApiV1 {
    private String accessJwt;
    private String refreshJwt;

    public static ResAuthPutDTOApiV1 of(String accessJwt, String refreshJwt) {
        return ResAuthPutDTOApiV1.builder()
            .accessJwt(accessJwt)
            .refreshJwt(refreshJwt)
            .build();
    }
}
