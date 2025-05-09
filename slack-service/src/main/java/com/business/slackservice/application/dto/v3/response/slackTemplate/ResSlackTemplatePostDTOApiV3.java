package com.business.slackservice.application.dto.v3.response.slackTemplate;

import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResSlackTemplatePostDTOApiV3 {
    @JsonProperty
    private SlackTemplate slackTemplate;

    public static ResSlackTemplatePostDTOApiV3 of(SlackTemplateEntity slackTemplateEntity) {
        return ResSlackTemplatePostDTOApiV3.builder()
            .slackTemplate(SlackTemplate.from(slackTemplateEntity))
            .build();
    }

    @Builder
    public static class SlackTemplate {
        @JsonProperty
        private String eventTypeName;
        @JsonProperty
        private String content;

        private static SlackTemplate from(SlackTemplateEntity slackEventTypeEntity) {
            return SlackTemplate.builder()
                .eventTypeName(slackEventTypeEntity.getSlackEventTypeEntity().getName())
                .content(slackEventTypeEntity.getContent())
                .build();
        }
    }
}
