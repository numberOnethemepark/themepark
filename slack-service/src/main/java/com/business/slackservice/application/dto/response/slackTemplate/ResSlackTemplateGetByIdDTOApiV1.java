package com.business.slackservice.application.dto.response.slackTemplate;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResSlackTemplateGetByIdDTOApiV1 {
    @JsonProperty
    private SlackTemplate slackTemplate;

    public static ResSlackTemplateGetByIdDTOApiV1 of(SlackTemplateEntity slackTemplateEntity) {
        return ResSlackTemplateGetByIdDTOApiV1.builder()
            .slackTemplate(SlackTemplate.from(slackTemplateEntity))
            .build();
    }

    @Builder
    public static class SlackTemplate {
        @JsonProperty
        private String slackTemplateName;
        @JsonProperty
        private String content;

        private static SlackTemplate from(SlackTemplateEntity slackTemplateEntity) {
            return SlackTemplate.builder()
                .slackTemplateName(slackTemplateEntity.getSlackEventTypeEntity().getName())
                .content(slackTemplateEntity.getContent())
                .build();
        }
    }
}
