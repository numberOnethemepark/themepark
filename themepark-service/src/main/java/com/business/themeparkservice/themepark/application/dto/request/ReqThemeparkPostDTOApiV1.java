package com.business.themeparkservice.themepark.application.dto.request;

import com.business.themeparkservice.themepark.domain.vo.ThemeparkType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqThemeparkPostDTOApiV1 {

    @Valid
    @NotNull(message = "테마파크 정보를 입력해주세요")
    private Themepark themepark;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Themepark {
        @NotBlank(message = "테마파크 이름을 입력해주세요")
        private String name;

        @NotBlank(message = "테마파크 설명을 입력해주세요")
        private String description;

        @NotNull(message = "테마파크 유형을 입력해주세요[AREA,RIDE]")
        private ThemeparkType type;

        @NotNull(message = "테마파크 운영 시작시간을 입력해주세요")
        private Time operationStartTime;

        @NotNull(message = "테마파크 운영 종료시간을 입력해주세요")
        private Time operationEndTime;

        private String heightLimit;

        @NotNull(message = "보호자 동반여부를 입력해주세요")
        private Boolean supervisor;

        @NotNull(message = "테마파크 설명 해시태그를 입력해주세요")
        private List<UUID> hashtag;
    }
}
