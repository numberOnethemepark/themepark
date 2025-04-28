package com.business.slackservice.application.dto.v1.response.slack;

import com.business.slackservice.domain.slack.entity.SlackEntity;
import com.business.slackservice.domain.slack.vo.SlackStatus;
import com.business.slackservice.domain.slack.vo.TargetType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Builder;

@Builder
public class ResSlackGetByIdDTOApiV1 {
    @JsonProperty
    private Slack slack;

    public static ResSlackGetByIdDTOApiV1 of (SlackEntity slackEntity) {
        return ResSlackGetByIdDTOApiV1.builder()
            .slack(Slack.from(slackEntity))
            .build();
    }

    @Builder
    public static class Slack {
        @JsonProperty
        private UUID slackEventTypeId;
        @JsonProperty
        private String relatedName;
        @JsonProperty
        private SlackStatus status;
        @JsonProperty
        private String content;
        @JsonProperty
        private String slackId;
        @JsonProperty
        private TargetType targetType;

        private static Slack from(SlackEntity slackEntity) {
            return Slack.builder()
                .slackEventTypeId(slackEntity.getSlackEventType().getId())
                .relatedName(slackEntity.getRelatedName())
                .status(slackEntity.getStatus())
                .content(slackEntity.getContent())
                .slackId(slackEntity.getTarget().getSlackId())
                .targetType(slackEntity.getTarget().getType())
                .build();
        }
    }
}
