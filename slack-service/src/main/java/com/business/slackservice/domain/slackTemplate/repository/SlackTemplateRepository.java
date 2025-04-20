package com.business.slackservice.domain.slackTemplate.repository;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import com.querydsl.core.types.Predicate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SlackTemplateRepository {

    SlackTemplateEntity save(SlackTemplateEntity slackTemplate);

    Optional<SlackTemplateEntity> findById(UUID id);

    Page<SlackTemplateEntity> findAll(Predicate predicate, Pageable pageable);

    boolean existsBySlackEventTypeEntity(SlackEventTypeEntity slackEventTypeEntity);

    Optional<SlackTemplateEntity> findBySlackEventTypeEntity(SlackEventTypeEntity slackEventTypeEntity);
}

