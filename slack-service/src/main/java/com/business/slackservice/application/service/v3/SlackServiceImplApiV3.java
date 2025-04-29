package com.business.slackservice.application.service.v3;

import com.business.slackservice.application.dto.v3.request.slack.ReqSlackPostDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slack.ResSlackGetByIdDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slack.ResSlackGetDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slack.ResSlackPostDTOApiV3;
import com.business.slackservice.application.exception.SlackExceptionCode;
import com.business.slackservice.application.service.SlackEventTypeService;
import com.business.slackservice.application.service.SlackMessageSender;
import com.business.slackservice.application.service.SlackTemplateService;
import com.business.slackservice.domain.slack.entity.SlackEntity;
import com.business.slackservice.domain.slack.repository.SlackRepository;
import com.business.slackservice.domain.slack.vo.SlackStatus;
import com.business.slackservice.domain.slack.vo.TargetType;
import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import com.business.slackservice.infrastructure.persistence.SlackRedisRepository;
import com.github.themepark.common.application.exception.CustomException;
import com.querydsl.core.types.Predicate;
import com.slack.api.methods.SlackApiException;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SlackServiceImplApiV3 implements SlackServiceApiV3 {

    @Value("${slack.admin.id}")
    private String slackAdminId;

    private final SlackEventTypeService slackEventTypeService;
    private final SlackTemplateService slackTemplateService;
    private final SlackRepository slackRepository;
    private final SlackMessageSender slackMessageSender;
    private final SlackRedisRepository slackRedisRepository;

    @KafkaListener(groupId = "group_a", topics = "slack-send")
    @Transactional
    @Override
    public ResSlackPostDTOApiV3 postBy(ReqSlackPostDTOApiV3 dto) throws SlackApiException, IOException {
        String eventTypeName = dto.getSlack().getSlackEventType();

        SlackEventTypeEntity eventType = getOrLoadEventType(eventTypeName);
        String templateContent = getOrLoadTemplateContent(eventTypeName, eventType);

        String content = generateContent(templateContent, dto.getSlack().getRelatedName());
        String slackId = resolveSlackId(dto);
        SlackStatus status = slackMessageSender.send(slackId, content);

        SlackEntity savedEntity = slackRepository.save(dto.getSlack().toEntityBy(eventType, content, status));
        return ResSlackPostDTOApiV3.of(savedEntity);
    }

    @Override
    public ResSlackGetByIdDTOApiV3 getBy(UUID id) {
        return ResSlackGetByIdDTOApiV3.of(findById(id));
    }

    @Override
    public ResSlackGetDTOApiV3 getBy(Predicate predicate, Pageable pageable) {
        Page<SlackEntity> slackEntityPage = slackRepository.findAll(predicate, pageable);
        return ResSlackGetDTOApiV3.of(slackEntityPage);
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

    private SlackEventTypeEntity getEventTypeByName(String slackEventTypeName) {
        return slackEventTypeService.getByName(slackEventTypeName);
    }

    private SlackTemplateEntity getTemplateBySlackEventType(
        SlackEventTypeEntity slackEventTypeEntity) {
        return slackTemplateService.findBySlackEventType(slackEventTypeEntity);
    }

    private String generateContent(String slackTemplateContent, String relatedName) {
        return slackTemplateContent.replace("{{relatedName}}", relatedName);
    }

    private String resolveSlackId(ReqSlackPostDTOApiV3 dto) {
        TargetType targetType = dto.getSlack().getTarget().getType();
        return targetType.equals(TargetType.USER_DM) ? dto.getSlack().getTarget().getSlackId() : slackAdminId;
    }

    private SlackEventTypeEntity getOrLoadEventType(String eventTypeName) {
        return slackRedisRepository.findSlackEventTypeByName(eventTypeName)
            .map(cached -> getEventTypeByName(eventTypeName))
            .orElseGet(() -> {
                SlackEventTypeEntity loaded = getEventTypeByName(eventTypeName);
                slackRedisRepository.saveSlackEventType(loaded);
                return loaded;
            });
    }

    private String getOrLoadTemplateContent(String eventTypeName, SlackEventTypeEntity eventType) {
        return slackRedisRepository.findSlackTemplateByEventType(eventTypeName)
            .orElseGet(() -> {
                SlackTemplateEntity loaded = getTemplateBySlackEventType(eventType);
                slackRedisRepository.saveSlackTemplate(loaded);
                return loaded.getContent();
            });
    }
}
