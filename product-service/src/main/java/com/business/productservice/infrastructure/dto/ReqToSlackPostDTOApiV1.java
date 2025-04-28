package com.business.productservice.infrastructure.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqToSlackPostDTOApiV1 {
    private Slack slack;

    public static ReqToSlackPostDTOApiV1 createStockSoldOutMessage(String productName) {
        Slack.SlackTarget target = Slack.SlackTarget.builder()
                .slackId("C01ABCDEF78")
                .type("ADMIN_CHANNEL")
                .build();

        Slack slack = Slack.builder()
                .slackEventType("STOCK_OUT")
                .relatedName(productName)
                .target(target)
                .build();

        return ReqToSlackPostDTOApiV1.builder()
                .slack(slack)
                .build();
    }


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
