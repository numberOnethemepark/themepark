package com.business.slackservice.application.service;

import com.business.slackservice.application.exception.SlackExceptionCode;
import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackEventType.repository.SlackEventTypeRepository;
import com.github.themepark.common.application.exception.CustomException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SlackEventTypeService {

    private final SlackEventTypeRepository slackEventTypeRepository;

    public SlackEventTypeEntity getBy(UUID id) {
        return slackEventTypeRepository.findById(id)
            .orElseThrow(() -> new CustomException(SlackExceptionCode.SLACK_EVENT_TYPE_NOT_FOUND));
    }
}
