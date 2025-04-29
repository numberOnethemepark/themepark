package com.business.slackservice.application.dto.v3.response.slackTemplate;

import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResSlackTemplateGetByIdDTOApiV3 {
    @JsonProperty
    private SlackTemplate slackTemplate;

    public static ResSlackTemplateGetByIdDTOApiV3 of(SlackTemplateEntity slackTemplateEntity) {
        return ResSlackTemplateGetByIdDTOApiV3.builder()
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
