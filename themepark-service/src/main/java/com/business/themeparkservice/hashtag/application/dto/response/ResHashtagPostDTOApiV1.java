package com.business.themeparkservice.hashtag.application.dto.response;

import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
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

    public static ResHashtagPostDTOApiV1 of(HashtagEntity reqDto) {
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

        public static Hashtag from(HashtagEntity reqDto) {
            return Hashtag.builder()
                    .id(reqDto.getId())
                    .name(reqDto.getName())
                    .build();
        }

    }
}
