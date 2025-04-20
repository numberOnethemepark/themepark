package com.business.slackservice.application.service;

import com.business.slackservice.application.dto.request.slack.ReqSlackPostDTOApiV1;
import com.business.slackservice.application.dto.response.slack.ResSlackGetByIdDTOApiV1;
import com.business.slackservice.application.dto.response.slack.ResSlackGetDTOApiV1;
import com.business.slackservice.application.dto.response.slack.ResSlackPostDTOApiV1;
import com.business.slackservice.application.exception.SlackExceptionCode;
import com.business.slackservice.domain.slack.entity.SlackEntity;
import com.business.slackservice.domain.slack.repository.SlackRepository;
import com.business.slackservice.domain.slack.vo.SlackStatus;
import com.business.slackservice.domain.slack.vo.TargetType;
import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import com.github.themepark.common.application.exception.CustomException;
import com.querydsl.core.types.Predicate;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SlackServiceImplApiV1 implements SlackServiceApiV1 {

    @Value("${slack.bot.token}")
    private String slackToken;

    @Value("${slack.admin.id}")
    private String slackAdminId;

    private final SlackEventTypeService slackEventTypeService;
    private final SlackTemplateService slackTemplateService;
    private final SlackRepository slackRepository;

    @Transactional
    @Override
    public ResSlackPostDTOApiV1 postBy(ReqSlackPostDTOApiV1 dto)
        throws SlackApiException, IOException {
        SlackEventTypeEntity eventType = getEventTypeById(dto.getSlack().getSlackEventTypeId());
        SlackTemplateEntity template = getTemplateBySlackEventType(eventType);

        String content = generateContent(template, dto.getSlack().getRelatedName());
        String slackId = resolveSlackId(dto);

        SlackStatus status = sendSlackMessage(slackId, content);

        SlackEntity slackEntity = dto.getSlack().toEntityBy(eventType, content, status);

        return ResSlackPostDTOApiV1.of(slackRepository.save(slackEntity));
    }

    @Override
    public ResSlackGetByIdDTOApiV1 getBy(UUID id) {
        return ResSlackGetByIdDTOApiV1.of(findById(id));
    }

    @Override
    public ResSlackGetDTOApiV1 getBy(Predicate predicate, Pageable pageable) {
        Page<SlackEntity> slackEntityPage = slackRepository.findAll(predicate, pageable);
        return ResSlackGetDTOApiV1.of(slackEntityPage);
    }

    @Transactional
    @Override
    public void deleteBy(UUID id, Long userId) {
        SlackEntity slackEntity = findById(id);
        slackEntity.deletedBy(userId);
    }

    private SlackEntity findById(UUID id) {
        return slackRepository.findById(id)
            .orElseThrow(() -> new CustomException(SlackExceptionCode.SLACK_NOT_FOUND));
    }

    private SlackEventTypeEntity getEventTypeById(UUID slackEventTypeId) {
        return slackEventTypeService.getBy(slackEventTypeId);
    }

    private SlackTemplateEntity getTemplateBySlackEventType(
        SlackEventTypeEntity slackEventTypeEntity) {
        return slackTemplateService.findBySlackEventType(slackEventTypeEntity);
    }

    private String generateContent(SlackTemplateEntity template, String relatedName) {
        return template.getContent().replace("{{relatedName}}", relatedName);
    }

    private String resolveSlackId(ReqSlackPostDTOApiV1 dto) {
        TargetType targetType = dto.getSlack().getTarget().getType();
        return targetType.equals(TargetType.USER_DM) ? dto.getSlack().getTarget().getSlackId() : slackAdminId;
    }

    private SlackStatus sendSlackMessage(String slackId, String content)
        throws SlackApiException, IOException {
        Slack slack = Slack.getInstance();

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
            .channel(slackId)
            .text(content)
            .build();

        ChatPostMessageResponse response = slack.methods(slackToken)
            .chatPostMessage(request);

        return response.isOk() ? SlackStatus.SENT : SlackStatus.FAILED;
    }
}
