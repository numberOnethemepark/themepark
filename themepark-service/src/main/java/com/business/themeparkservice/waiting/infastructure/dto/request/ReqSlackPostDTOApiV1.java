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
                .slackEventTypeId(UUID.fromString("5edff6f0-b211-4fb4-b106-641a8fcfe8d1"))
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
        private UUID slackEventTypeId;
        private String relatedName;
        private SlackTarget target;

        @Getter
        @Builder
        public static class SlackTarget {
            private String slackId;
            private TargetType type;
        }

//        public SlackEntity toEntityBy(SlackEventTypeEntity slackEventTypeEntity, String content, SlackStatus status) {
//            return SlackEntity.create(
//                slackEventTypeEntity,
//                relatedName,
//                status,
//                content,
//                target.getSlackId(),
//                target.getType()
//            );
//        }
    }
}
