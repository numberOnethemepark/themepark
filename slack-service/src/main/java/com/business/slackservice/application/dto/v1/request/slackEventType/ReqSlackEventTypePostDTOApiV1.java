package com.business.slackservice.application.dto.v1.request.slackEventType;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReqSlackEventTypePostDTOApiV1 {

    @Valid
    @NotNull(message = "이벤트 유형을 입력해주세요.")
    private SlackEventType slackEventType;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class SlackEventType {
        @NotNull(message = "이벤트 이름을 입력해주세요.")
        private String name;

        public SlackEventTypeEntity toEntity() {
            return SlackEventTypeEntity.create(name);
        }
    }
}
