package com.business.themeparkservice.hashtag.application.dto.response;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPostDTOApiV1;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResHashtagPostDTOApiV1 {

    private Hashtag hashtag;

    public static ResHashtagPostDTOApiV1 of(ReqHashtagPostDTOApiV1 reqDto) {
        return ResHashtagPostDTOApiV1.builder()
                .hashtag(Hashtag.from(reqDto))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Hashtag{
        private UUID id;
        private String name;

        public static Hashtag from(ReqHashtagPostDTOApiV1 reqDto) {
            return Hashtag.builder()
                    .id(UUID.fromString("a6e49e7h-1eaf-478f-bb36-d73b66330f79"))
                    .name(reqDto.getHashtag().getName())
                    .build();
        }

    }
}
