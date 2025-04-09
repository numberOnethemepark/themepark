package com.business.themeparkservice.themepark.application.dto.response;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
import com.business.themeparkservice.themepark.domain.vo.ThemeparkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResThemeparkGetDTOApiV1 {

    private ThemeparkPage themeparkPage;

    public static ResThemeparkGetDTOApiV1 of(Page<ThemeparkEntity> themeparkEntityPage){
        return ResThemeparkGetDTOApiV1.builder()
                .themeparkPage(new ThemeparkPage(themeparkEntityPage))
                .build();
    }

    @Getter
    @ToString
    public static class ThemeparkPage extends PagedModel<ThemeparkPage.Themepark> {
        public ThemeparkPage(Page<ThemeparkEntity> themeparkEntityPage) {
            super(
                    new PageImpl<>(
                            Themepark.from(themeparkEntityPage.getContent()),
                            themeparkEntityPage.getPageable(),
                            themeparkEntityPage.getTotalElements()
                    )
            );
        }

        @Getter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Themepark{
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

            public static List<Themepark> from(List<ThemeparkEntity> themeparkEntityList) {
                return themeparkEntityList.stream()
                        .map(Themepark::from)
                        .toList();
            }

            public static Themepark from(ThemeparkEntity entity) {
                List<String> stringList = List.of("신나는","즐거운");

                return Themepark.builder()
                        .id(UUID.fromString("f5e49e7d-3baf-478f-bb36-d73b66330f79"))
                        .name("놀이기구1")
                        .description("슝슝 놀이기구입니다.")
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
                            .map(name-> Hashtag.from(name))
                            .toList();
                }
            }
        }

    }


}
