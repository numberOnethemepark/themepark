package com.business.themeparkservice.themepark.application.dto.response;

import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
import com.business.themeparkservice.themepark.domain.vo.ThemeparkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResThemeparkPostDTOApiv1 {

    private ThemePark themepark;

    public static ResThemeparkPostDTOApiv1 of(ReqThemeparkPostDTOApiV1 reqDto) {
        return ResThemeparkPostDTOApiv1.builder()
                .themepark(ResThemeparkPostDTOApiv1.ThemePark.from(reqDto))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThemePark {
        private UUID id;
        private String name;
        private String description;
        private ThemeparkType type;

        @JsonFormat(pattern = "HH:mm")
        private LocalTime operationStartTime;

        @JsonFormat(pattern = "HH:mm")
        private LocalTime operationEndTime;

        private String heightLimit;
        private String supervisor;
        private Hashtag hashtag;

        public static ThemePark from(ReqThemeparkPostDTOApiV1 reqDto){
            Hashtag hashtag = new Hashtag();
            hashtag.setName(List.of("name1","name2"));

            return ThemePark.builder()
                    .id(UUID.fromString("f5e49e7d-3baf-478f-bb36-d73b66330f79"))
                    .name(reqDto.getThemepark().getName())
                    .description(reqDto.getThemepark().getDescription())
                    .type(reqDto.getThemepark().getType())
                    .operationStartTime(reqDto.getThemepark().getOperationStartTime())
                    .operationEndTime(reqDto.getThemepark().getOperationEndTime())
                    .heightLimit(reqDto.getThemepark().getHeightLimit())
                    .supervisor(String.valueOf(reqDto.getThemepark().getSupervisor()))
                    .hashtag(hashtag)
                    .build();
        }

        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Hashtag {
            private List<String> name;
        }
    }
}
