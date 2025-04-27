package com.business.themeparkservice.waiting.infastructure.feign.dto.request;

import com.business.themeparkservice.waiting.infastructure.feign.vo.TargetType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReqKafkaPostDTOApiV1 {
    private Kafka kafka;

    public void createslack(String message) {
        this.kafka = Kafka.builder()
                .slackEventType("WAITING_CALL")
                .relatedName(message)
                .target(Kafka.SlackTarget.builder()
                        .slackId("U08L3T2DTCP")
                        .type(TargetType.USER_DM)
                        .build()
                )
                .build();
    }

    @Getter
    @Builder
    public static class Kafka {
        private String slackEventType;
        private String relatedName;
        private SlackTarget target;

        @Getter
        @Builder
        public static class SlackTarget {
            private String slackId;
            private TargetType type;
        }

    }
}
