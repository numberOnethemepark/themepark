package com.business.slackservice.application.dto.v1.response.slackEventType;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

@Builder
public class ResSlackEventTypeGetDTOApiV1 {

    @JsonProperty
    private SlackEventTypePage slackEventTypePage;

    public static ResSlackEventTypeGetDTOApiV1 of(
        Page<SlackEventTypeEntity> slackEventTypeEntityPage) {
        return ResSlackEventTypeGetDTOApiV1.builder()
            .slackEventTypePage(new SlackEventTypePage(slackEventTypeEntityPage))
            .build();
    }

    public static class SlackEventTypePage extends PagedModel<SlackEventTypePage.SlackEventType> {

        public SlackEventTypePage(Page<SlackEventTypeEntity> slackEventTypeInfoPage) {
            super(
                new PageImpl<>(
                    SlackEventType.from(slackEventTypeInfoPage.getContent()),
                    slackEventTypeInfoPage.getPageable(),
                    slackEventTypeInfoPage.getTotalElements()
                )
            );
        }

        @Builder
        public static class SlackEventType {

            @JsonProperty
            String name;

            public static List<SlackEventType> from(
                List<SlackEventTypeEntity> slackEventTypeEntityList) {
                return slackEventTypeEntityList.stream()
                    .map(SlackEventType::from)
                    .toList();
            }

            public static SlackEventType from(SlackEventTypeEntity slackEventTypeEntity) {
                return SlackEventType.builder()
                    .name(slackEventTypeEntity.getName())
                    .build();
            }
        }
    }
}