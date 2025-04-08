package com.business.themeparkservice.hashtag.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqHashtagPostDTOApiV1 {

    private Hashtag hashtag;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Hashtag{
        private String name;
    }
}
