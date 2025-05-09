package com.business.slackservice.application.dto.v3.request.slack;

import com.business.slackservice.domain.slack.entity.SlackEntity;
import com.business.slackservice.domain.slack.vo.SlackStatus;
import com.business.slackservice.domain.slack.vo.TargetType;
import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqSlackPostDTOApiV3 {
    private Slack slack;


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Slack {
        private String slackEventType;
        private String relatedName;
        private SlackTarget target;

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SlackTarget {
            private String slackId;
            private TargetType type;
        }

        public SlackEntity toEntityBy(SlackEventTypeEntity slackEventTypeEntity, String content, SlackStatus status) {
            return SlackEntity.create(
                slackEventTypeEntity,
                relatedName,
                status,
                content,
                target.getSlackId(),
                target.getType()
            );
        }
    }
}
