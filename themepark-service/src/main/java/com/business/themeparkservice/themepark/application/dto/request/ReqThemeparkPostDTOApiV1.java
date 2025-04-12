package com.business.themeparkservice.themepark.application.dto.request;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
import com.business.themeparkservice.themepark.domain.vo.ThemeparkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqThemeparkPostDTOApiV1 {

    @Valid
    @NotNull(message = "테마파크 정보를 입력해주세요")
    private ThemePark themepark;

    public ThemeparkEntity createThemepark() {
        return ThemeparkEntity.builder()
                .name(themepark.getName())
                .description(themepark.getDescription())
                .type(themepark.getType())
                .operationStartTime(themepark.getOperationStartTime())
                .operationEndTime(themepark.getOperationEndTime())
                .heightLimit(themepark.getHeightLimit())
                .supervisor(themepark.getSupervisor())
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThemePark {
        @NotBlank(message = "테마파크 이름을 입력해주세요")
        private String name;

        @NotBlank(message = "테마파크 설명을 입력해주세요")
        private String description;

        @NotNull(message = "테마파크 유형을 입력해주세요[AREA,RIDE]")
        private ThemeparkType type;

        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "테마파크 운영 시작시간을 입력해주세요")
        private LocalTime operationStartTime;

        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "테마파크 운영 종료시간을 입력해주세요")
        private LocalTime operationEndTime;

        private String heightLimit;

        @NotNull(message = "보호자 동반여부를 입력해주세요")
        private Boolean supervisor;

        @NotNull(message = "테마파크 설명 해시태그를 입력해주세요")
        private List<Hashtag> hashtagList;


        @Getter
        @Builder
        public static class Hashtag{
            private UUID hashtagId;
            private String name;
        }
    }
}
