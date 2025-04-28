package com.business.slackservice.application.dto.v2.response.slackEventType;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResSlackEventTypeGetByIdDTOApiV2 {
    @JsonProperty
    private SlackEventType slackEventType;

    public static ResSlackEventTypeGetByIdDTOApiV2 of(SlackEventTypeEntity slackEventTypeEntity) {
        return ResSlackEventTypeGetByIdDTOApiV2.builder()
            .slackEventType(SlackEventType.from(slackEventTypeEntity))
            .build();
    }

    @Builder
    public static class SlackEventType {
        @JsonProperty
        private String name;

        private static SlackEventType from(SlackEventTypeEntity slackEventTypeEntity) {
            return SlackEventType.builder()
                .name(slackEventTypeEntity.getName())
                .build();
        }
    }
}
