package com.business.slackservice.infrastructure.persistence;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import java.util.Optional;

public interface SlackRedisRepository {
    void saveSlackEventType(SlackEventTypeEntity slackEventType);
    void saveSlackTemplate(SlackTemplateEntity template);
    Optional<String> findSlackEventTypeByName(String name);
    Optional<String> findSlackTemplateByEventType(String eventType);
}
