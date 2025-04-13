package com.business.themeparkservice.hashtag.application.dto.request;

import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqHashtagPostDTOApiV1 {
    @Valid
    @NotNull(message = "해시태그 정보를 입력해주세요")
    private Hashtag hashtag;

    public HashtagEntity createHashtag() {
        return HashtagEntity.builder()
                .hashtagName(hashtag.getName())
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Hashtag{
        @NotBlank(message = "해시태그 이름을 입력해주세요")
        private String name;
    }
}
