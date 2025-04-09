package com.business.themeparkservice.themepark.application.dto.response;

import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPutDTOApiV1;
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
public class ResThemeparkPutDTOApiV1 {

    private ThemePark themepark;

    public static ResThemeparkPutDTOApiV1 of(ReqThemeparkPutDTOApiV1 reqDto,UUID id) {
        return ResThemeparkPutDTOApiV1.builder()
                .themepark(ThemePark.from(reqDto,id))
                .build();
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThemePark{
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
        private List<Hashtag> hashtagList;

        public static ThemePark from(ReqThemeparkPutDTOApiV1 reqDto,UUID id) {
            List<String> stringList = List.of("신나는","즐거운");

            return ThemePark.builder()
                    .id(id)
                    .name("놀이기구1")
                    .description("슝슝 놀이기구입니다.")
                    .type(ThemeparkType.valueOf("RIDE"))
                    .operationStartTime(LocalTime.parse("10:00"))
                    .operationEndTime(LocalTime.parse("18:00"))
                    .heightLimit(reqDto.getThemepark().getHeightLimit())
                    .supervisor(true)
                    .hashtagList(Hashtag.from(stringList))
                    .build();
        }

        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Hashtag {
            private String name;

            public static Hashtag from(String name){
                return Hashtag.builder()
                        .name(name)
                        .build();
            }

            public static List<Hashtag> from(List<String> nameList) {
                return nameList.stream()
                        .map(name-> Hashtag.from(name))
                        .toList();
            }
        }
    }
}
