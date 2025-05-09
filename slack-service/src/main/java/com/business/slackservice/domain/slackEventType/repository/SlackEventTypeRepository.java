package com.business.slackservice.domain.slackEventType.repository;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.querydsl.core.types.Predicate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SlackEventTypeRepository {

    SlackEventTypeEntity save(SlackEventTypeEntity slackEventTypeEntity);

    Optional<SlackEventTypeEntity> findById(UUID id);

    Page<SlackEventTypeEntity> findAll(Predicate predicate, Pageable pageable);

    boolean existsByName(String name);

    Optional<SlackEventTypeEntity> findByName(String slackEventTypeName);
}
