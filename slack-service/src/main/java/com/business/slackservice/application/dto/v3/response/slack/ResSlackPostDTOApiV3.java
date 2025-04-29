package com.business.slackservice.application.dto.v3.response.slack;

import com.business.slackservice.domain.slack.entity.SlackEntity;
import com.business.slackservice.domain.slack.vo.SlackStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResSlackPostDTOApiV3 {
    @JsonProperty
    private Slack slack;

    public static ResSlackPostDTOApiV3 of(SlackEntity slackEntity) {
        return ResSlackPostDTOApiV3.builder()
            .slack(Slack.from(slackEntity))
            .build();
    }

    @Builder
    public static class Slack {
        @JsonProperty
        private String content;
        @JsonProperty
        private SlackStatus status;

        private static Slack from(SlackEntity slackEntity) {
            return Slack.builder()
                .content(slackEntity.getContent())
                .status(slackEntity.getStatus())
                .build();
        }
    }

}
