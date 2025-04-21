package com.business.themeparkservice.waiting.infastructure.dto.request;

import com.business.themeparkservice.waiting.infastructure.vo.TargetType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ReqSlackPostDTOApiV1 {
    private Slack slack;

    public void createslack(String message) {
        this.slack = Slack.builder()
                .slackEventType("WAITING_CALL")
                .relatedName(message)
                .target(Slack.SlackTarget.builder()
                        .slackId("U08L3T2DTCP")
                        .type(TargetType.USER_DM)
                        .build()
                )
                .build();
    }

    @Getter
    @Builder
    public static class Slack {
        private String slackEventType;
        private String relatedName;
        private SlackTarget target;

        @Getter
        @Builder
        public static class SlackTarget {
            private String slackId;
            private TargetType type;
        }

    }
}
