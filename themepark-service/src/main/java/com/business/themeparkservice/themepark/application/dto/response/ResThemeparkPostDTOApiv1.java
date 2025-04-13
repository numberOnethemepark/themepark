package com.business.themeparkservice.themepark.application.dto.response;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
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
public class ResThemeparkPostDTOApiv1 {

    private ThemePark themepark;

    public static ResThemeparkPostDTOApiv1 of(ThemeparkEntity themeparkEntity, List<String> hashtagNames) {
        return ResThemeparkPostDTOApiv1.builder()
                .themepark(ResThemeparkPostDTOApiv1.ThemePark.from(themeparkEntity,hashtagNames))
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

        public static ThemePark from(ThemeparkEntity themeparkEntity,List<String> hashtagNames) {
            return ThemePark.builder()
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

            public static List<Hashtag> from(List<String> nameList) {
                return nameList.stream()
                        .map(Hashtag::from)
                        .toList();
            }
        }
    }
}
