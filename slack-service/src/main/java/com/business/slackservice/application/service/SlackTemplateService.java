package com.business.slackservice.application.service;

import com.business.slackservice.application.exception.SlackExceptionCode;
import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import com.business.slackservice.domain.slackTemplate.repository.SlackTemplateRepository;
import com.github.themepark.common.application.exception.CustomException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SlackTemplateService {
    private final SlackTemplateRepository slackTemplateRepository;

    public SlackTemplateEntity getBy(UUID id) {
        return slackTemplateRepository.findById(id)
            .orElseThrow(() -> new CustomException(SlackExceptionCode.SLACK_TEMPLATE_NOT_FOUND));

    }

    public SlackTemplateEntity findBySlackEventType(SlackEventTypeEntity slackEventTypeEntity) {
        return slackTemplateRepository.findBySlackEventTypeEntity(slackEventTypeEntity)
            .orElseThrow(() -> new CustomException(SlackExceptionCode.SLACK_TEMPLATE_NOT_FOUND));
    }
}
