package com.business.slackservice.application.dto.v3.response.slack;

import com.business.slackservice.domain.slack.entity.SlackEntity;
import com.business.slackservice.domain.slack.vo.SlackStatus;
import com.business.slackservice.domain.slack.vo.TargetType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

@Builder
public class ResSlackGetDTOApiV3 {

    @JsonProperty
    private SlackPage slackPage;

    public static ResSlackGetDTOApiV3 of(Page<SlackEntity> slackEntityPage) {
        return ResSlackGetDTOApiV3.builder()
            .slackPage(new SlackPage(slackEntityPage))
            .build();
    }

    public static class SlackPage extends PagedModel<SlackPage.Slack> {
        public SlackPage(Page<SlackEntity> slackInfoPage) {
            super(
                new PageImpl<>(
                    Slack.from(slackInfoPage.getContent()),
                    slackInfoPage.getPageable(),
                    slackInfoPage.getTotalElements()
                )
            );
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

            public static List<Slack> from(
                List<SlackEntity> slackEntityList
            ) {
                return slackEntityList.stream()
                    .map(Slack::from)
                    .toList();
            }

            public static Slack from(SlackEntity slackEntity) {
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
}
