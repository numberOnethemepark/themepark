package com.business.slackservice.application.dto.request.slack;

import com.business.slackservice.domain.slack.entity.SlackEntity;
import com.business.slackservice.domain.slack.vo.SlackStatus;
import com.business.slackservice.domain.slack.vo.TargetType;
import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ReqSlackPostDTOApiV1 {
    private Slack slack;

    @Getter
    public static class Slack {
        private UUID slackEventTypeId;
        private String relatedName;
        private SlackTarget target;

        @Getter
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
