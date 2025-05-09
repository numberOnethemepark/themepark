package com.business.slackservice.infrastructure.persistence;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlackRedisRepositoryImpl implements SlackRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private final String EVENT_TYPE_PREFIX = "slack:event:";
    private final String TEMPLATE_PREFIX = "slack:template:";

    @Override
    public void saveSlackEventType(SlackEventTypeEntity slackEventType) {
        redisTemplate.opsForValue().set(EVENT_TYPE_PREFIX + slackEventType.getName(), slackEventType.getId());
    }

    @Override
    public void saveSlackTemplate(SlackTemplateEntity template) {
        redisTemplate.opsForValue().set(TEMPLATE_PREFIX + template.getSlackEventTypeEntity().getName(), template.getContent());
    }

    @Override
    public Optional<String> findSlackEventTypeByName(String name) {
        return Optional.ofNullable((String) redisTemplate.opsForValue().get(EVENT_TYPE_PREFIX + name));
    }

    @Override
    public Optional<String> findSlackTemplateByEventType(String eventType) {
        return Optional.ofNullable((String) redisTemplate.opsForValue().get(TEMPLATE_PREFIX + eventType));
    }
}
