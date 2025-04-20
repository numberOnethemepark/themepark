package com.business.slackservice.infrastructure.persistence;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackEventType.repository.SlackEventTypeRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SlackEventTypeJpaRepository extends JpaRepository<SlackEventTypeEntity, UUID>,
    SlackEventTypeRepository, QuerydslPredicateExecutor<SlackEventTypeEntity> {

}
