package com.business.themeparkservice.themepark.application.dto.response;

import lombok.*;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResThemeparkPostDTOApiv1 {

    private Themepark themepark;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Themepark {
        private UUID id;
        private String name;
        private String description;
        private String type;
        private Time operationStartTime;
        private Time operationEndTime;
        private String heightLimit;
        private String supervisor;
        private Hashtag hashtag;

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Hashtag {
            private List<String> name;
        }
    }
}
