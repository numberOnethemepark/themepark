package com.business.slackservice.application.dto.v2.request.slackTemplate;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReqSlackTemplatePostDTOApiV2 {
    @Valid
    @NotNull(message = "슬랙 양식을 입력해주세요.")
    private SlackTemplate slackTemplate;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class SlackTemplate {
        @NotNull(message = "이벤트 타입 ID를 입력해주세요.")
        private UUID slackEventTypeId;
        @NotNull(message = "양식을 입력해주세요.")
        private String content;

        public SlackTemplateEntity toEntityBy(SlackEventTypeEntity slackEventTypeEntity) {
            return SlackTemplateEntity.create(slackEventTypeEntity, content);
        }
    }
}
