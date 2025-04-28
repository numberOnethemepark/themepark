package com.business.slackservice.application.dto.v1.response.slackTemplate;

import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResSlackTemplatePostDTOApiV1 {
    @JsonProperty
    private SlackTemplate slackTemplate;

    public static ResSlackTemplatePostDTOApiV1 of(SlackTemplateEntity slackTemplateEntity) {
        return ResSlackTemplatePostDTOApiV1.builder()
            .slackTemplate(ResSlackTemplatePostDTOApiV1.SlackTemplate.from(slackTemplateEntity))
            .build();
    }

    @Builder
    public static class SlackTemplate {
        @JsonProperty
        private String eventTypeName;
        @JsonProperty
        private String content;

        private static ResSlackTemplatePostDTOApiV1.SlackTemplate from(SlackTemplateEntity slackEventTypeEntity) {
            return SlackTemplate.builder()
                .eventTypeName(slackEventTypeEntity.getSlackEventTypeEntity().getName())
                .content(slackEventTypeEntity.getContent())
                .build();
        }
    }
}
