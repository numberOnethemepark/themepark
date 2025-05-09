package com.business.slackservice.application.dto.v3.response.slackEventType;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResSlackEventTypeGetByIdDTOApiV3 {
    @JsonProperty
    private SlackEventType slackEventType;

    public static ResSlackEventTypeGetByIdDTOApiV3 of(SlackEventTypeEntity slackEventTypeEntity) {
        return ResSlackEventTypeGetByIdDTOApiV3.builder()
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
