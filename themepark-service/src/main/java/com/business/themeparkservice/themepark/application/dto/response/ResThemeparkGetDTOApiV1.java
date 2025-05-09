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
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResThemeparkGetDTOApiV1 {

    private ThemeparkPage themeparkPage;

    public static ResThemeparkGetDTOApiV1 of(Page<ThemeparkEntity> themeparkEntityPage, Map<UUID, List<String>> hashtagMap){
        return ResThemeparkGetDTOApiV1.builder()
                .themeparkPage(new ThemeparkPage(themeparkEntityPage, hashtagMap))
                .build();
    }

    @Getter
    @ToString
    public static class ThemeparkPage extends PagedModel<ThemeparkPage.Themepark> {
        public ThemeparkPage(Page<ThemeparkEntity> themeparkEntityPage, Map<UUID, List<String>> hashtagMap) {
            super(
                    new PageImpl<>(
                            Themepark.from(themeparkEntityPage.getContent(),hashtagMap),
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

            public static List<Themepark> from(List<ThemeparkEntity> themeparkEntityList,Map<UUID, List<String>> hashtagMap) {
                return themeparkEntityList.stream()
                        .map(themepark -> Themepark.from(
                                themepark,
                                hashtagMap.getOrDefault(themepark.getId(), List.of())//id가 존재하는경우 해시태그 값을 가져오도록
                        ))
                        .toList();
            }

            public static Themepark from(ThemeparkEntity themeparkEntity, List<String> hashtagNames) {

                return Themepark.builder()
                        .id(themeparkEntity.getId())
                        .name(themeparkEntity.getName())
                        .description(themeparkEntity.getDescription())
                        .type(themeparkEntity.getType())
                        .operationStartTime(themeparkEntity.getOperationStartTime())
                        .operationEndTime(themeparkEntity.getOperationEndTime())
                        .heightLimit(themeparkEntity.getHeightLimit())
                        .supervisor(themeparkEntity.isSupervisor())
                        .hashtagList(Hashtag.from(hashtagNames))
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

                public static List<Hashtag> from(List<String> hashtagNames) {
                    return hashtagNames.stream()
                            .map(Hashtag::from)
                            .toList();
                }
            }
        }

    }


}
