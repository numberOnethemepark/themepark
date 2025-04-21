package com.business.productservice.infrastructure.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqToSlackPostDTOApiV1 {
    private Slack slack;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Slack {
        private String slackEventType;
        private String relatedName;
        private SlackTarget target;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class SlackTarget {
            private String slackId;
            private String type;
        }
    }
}
