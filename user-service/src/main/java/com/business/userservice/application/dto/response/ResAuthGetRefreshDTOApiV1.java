package com.business.userservice.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResAuthGetRefreshDTOApiV1 {
    private String accessJwt;

    public static ResAuthGetRefreshDTOApiV1 of(String accessJwt) {
        return ResAuthGetRefreshDTOApiV1.builder()
            .accessJwt(accessJwt)
            .build();
    }
}
