package com.business.slackservice.application.dto.response.slackEventType;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResSlackEventTypeGetByIdDTOApiV1 {
    @JsonProperty
    private SlackEventType slackEventType;

    public static ResSlackEventTypeGetByIdDTOApiV1 of(SlackEventTypeEntity slackEventTypeEntity) {
        return ResSlackEventTypeGetByIdDTOApiV1.builder()
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
