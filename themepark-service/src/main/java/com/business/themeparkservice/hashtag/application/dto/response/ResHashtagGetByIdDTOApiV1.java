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
public class ResHashtagGetByIdDTOApiV1 {
    private Hashtag hashtag;

    public static ResHashtagGetByIdDTOApiV1 of(HashtagEntity hashtagEntity) {
        return ResHashtagGetByIdDTOApiV1.builder()
                .hashtag(Hashtag.from(hashtagEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Hashtag{
        private UUID id;
        private String name;

        public static Hashtag from(HashtagEntity hashtagEntity) {
            return Hashtag.builder()
                    .id(hashtagEntity.getId())
                    .name(hashtagEntity.getName())
                    .build();
        }
    }
}
