package com.business.slackservice.domain.slack.repository;

import com.business.slackservice.domain.slack.entity.SlackEntity;
import com.querydsl.core.types.Predicate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SlackRepository {

    SlackEntity save(SlackEntity slackEntity);

    Optional<SlackEntity> findById(UUID id);

    Page<SlackEntity> findAll(Predicate predicate, Pageable pageable);
}
