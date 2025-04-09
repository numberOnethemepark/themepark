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

    public static ResThemeparkPostDTOApiv1 of() {
        return ResThemeparkPostDTOApiv1.builder()
                .themepark(ResThemeparkPostDTOApiv1.ThemePark.from())
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
        private Boolean supervisor;
        private List<Hashtag> hashtagList;

        public static ThemePark from(){
            List<String> stringList = List.of("신나는","즐거운");

            return ThemePark.builder()
                    .id(UUID.fromString("f5e49e7d-3baf-478f-bb36-d73b66330f79"))
                    .name("테마파크1")
                    .description("테마파크설명")
                    .type(ThemeparkType.valueOf("RIDE"))
                    .operationStartTime(LocalTime.parse("10:00"))
                    .operationEndTime(LocalTime.parse("18:00"))
                    .heightLimit("130~180cm")
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
                        .map(name->Hashtag.from(name))
                        .toList();
            }
        }
    }
}
