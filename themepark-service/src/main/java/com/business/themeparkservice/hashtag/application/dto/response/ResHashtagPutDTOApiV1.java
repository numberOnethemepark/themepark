package com.business.themeparkservice.hashtag.application.dto.response;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPutDTOApiV1;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResHashtagPutDTOApiV1 {
    private Hashtag hashtag;
    public static ResHashtagPutDTOApiV1 of(UUID id, ReqHashtagPutDTOApiV1 reqDto) {
        return ResHashtagPutDTOApiV1.builder()
                .hashtag(Hashtag.from(id,reqDto))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Hashtag {
        private UUID id;
        private String name;

        public static Hashtag from(UUID id, ReqHashtagPutDTOApiV1 reqDto) {
            return Hashtag.builder()
                    .id(id)
                    .name(reqDto.getHashtag().getName())
                    .build();
        }
    }
}
