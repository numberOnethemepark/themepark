package com.business.themeparkservice.themepark.application.dto.response;

import com.business.themeparkservice.themepark.domain.vo.ThemeparkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResThemeparkGetByIdDTOApiV1 {
    public static ResThemeparkGetByIdDTOApiV1 of(UUID id) {
        return ResThemeparkGetByIdDTOApiV1.builder()
                .themePark(ThemePark.from(id))
                .build();
    }

    private ThemePark themePark;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ThemePark{
        private UUID id;
        private String name;
        private String description;
        private ThemeparkType type;

        @JsonFormat(pattern = "HH:mm")
        private LocalTime operationStartTime;

        @JsonFormat(pattern = "HH:mm")
        private LocalTime operationEndTime;

        private String heightLimit;
        private Boolean supervisor;
        private Hashtag hashtag;

        public static ThemePark from(UUID id){
            Hashtag hashtag = new Hashtag();
            hashtag.setName(List.of("name1","name2"));

            return ThemePark.builder()
                    .id(id)
                    .name("놀이기구1")
                    .description("슝슝 놀이기구입니다.")
                    .type(ThemeparkType.valueOf("RIDE"))
                    .operationStartTime(LocalTime.parse("10:00"))
                    .operationEndTime(LocalTime.parse("18:00"))
                    .heightLimit("130~180cm")
                    .supervisor(true)
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
