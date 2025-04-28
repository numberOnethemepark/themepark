package com.business.slackservice.application.dto.v2.request.slackTemplate;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReqSlackTemplatePutDTOApiV2 {
    @Valid
    @NotNull(message = "슬랙 양식을 입력해주세요.")
    private SlackTemplate slackTemplate;

    @Builder
    @Getter
    public static class SlackTemplate {
        private UUID SlackEventTypeId;
        private String content;

        public void update(SlackTemplateEntity slackTemplateEntity, SlackEventTypeEntity slackEventTypeEntity) {
            slackTemplateEntity.update(slackEventTypeEntity, content);
        }
    }
}
