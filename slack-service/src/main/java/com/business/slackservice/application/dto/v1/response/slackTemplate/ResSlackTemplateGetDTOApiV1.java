package com.business.slackservice.application.dto.v1.response.slackTemplate;

import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

@Builder
public class ResSlackTemplateGetDTOApiV1 {
    @JsonProperty
    private SlackTemplatePage slackTemplatePage;

    public static ResSlackTemplateGetDTOApiV1 of(
        Page<SlackTemplateEntity> slackTemplateEntityPage
    ) {
        return ResSlackTemplateGetDTOApiV1.builder()
            .slackTemplatePage(new SlackTemplatePage(slackTemplateEntityPage))
            .build();
    }

    public static class SlackTemplatePage extends PagedModel<SlackTemplatePage.SlackTemplate> {
        public SlackTemplatePage(Page<SlackTemplateEntity> slackTemplateInfoPage) {
            super(
                new PageImpl<>(
                    SlackTemplate.from(slackTemplateInfoPage.getContent()),
                    slackTemplateInfoPage.getPageable(),
                    slackTemplateInfoPage.getTotalElements()
                )
            );
        }

        @Builder
        public static class SlackTemplate {
            @JsonProperty
            String eventTypeName;
            @JsonProperty
            String content;

            public static List<SlackTemplate> from(
                List<SlackTemplateEntity> slackTemplateEntityList
            ) {
                return slackTemplateEntityList.stream()
                    .map(SlackTemplate::from)
                    .toList();
            }

            public static SlackTemplate from(SlackTemplateEntity slackTemplateEntity) {
                return SlackTemplate.builder()
                    .eventTypeName(slackTemplateEntity.getSlackEventTypeEntity().getName())
                    .content(slackTemplateEntity.getContent())
                    .build();
            }
        }
    }
}
