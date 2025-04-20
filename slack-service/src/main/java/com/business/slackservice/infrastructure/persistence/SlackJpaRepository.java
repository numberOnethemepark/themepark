package com.business.slackservice.infrastructure.persistence;

import com.business.slackservice.domain.slack.entity.SlackEntity;
import com.business.slackservice.domain.slack.repository.SlackRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SlackJpaRepository extends JpaRepository<SlackEntity, UUID>, SlackRepository,
    QuerydslPredicateExecutor<SlackEntity> {

}
