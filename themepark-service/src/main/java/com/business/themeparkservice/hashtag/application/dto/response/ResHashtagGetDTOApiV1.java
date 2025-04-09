package com.business.themeparkservice.hashtag.application.dto.response;

import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResHashtagGetDTOApiV1 {

    private HashtagPage hashtagPage;

    public static ResHashtagGetDTOApiV1 of(Page<HashtagEntity> tempHashtagPage) {
        return ResHashtagGetDTOApiV1.builder()
                .hashtagPage(new HashtagPage(tempHashtagPage))
                .build();
    }

    @Getter
    @ToString
    public static class HashtagPage extends PagedModel<HashtagPage.Hashtag> {
        public HashtagPage(Page<HashtagEntity> hashtagEntityPage) {
            super(
                    new PageImpl<>(
                            Hashtag.from(hashtagEntityPage.getContent()),
                            hashtagEntityPage.getPageable(),
                            hashtagEntityPage.getTotalElements()

                    )
            );
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Hashtag {
            private UUID id;
            private String name;

            public static List<Hashtag> from(List<HashtagEntity> hashtagEntityList) {
                return hashtagEntityList.stream()
                        .map(Hashtag::from)
                        .toList();
            }

            public static Hashtag from(HashtagEntity entity) {
                return Hashtag.builder()
                        .id(UUID.fromString("f5e49e5d-3baf-478f-bb36-d70b66330f79"))
                        .name("신나는")
                        .build();
            }

        }





    }
}
