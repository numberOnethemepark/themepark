package com.business.themeparkservice.hashtag.application.dto.response;

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

    public static ResHashtagGetByIdDTOApiV1 of(UUID id) {
        return ResHashtagGetByIdDTOApiV1.builder()
                .hashtag(Hashtag.from(id))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Hashtag{
        private UUID id;
        private String name;

        public static Hashtag from(UUID id) {
            return Hashtag.builder()
                    .id(id)
                    .name("신나는")
                    .build();
        }
    }
}
