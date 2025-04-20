package com.business.slackservice.infrastructure.persistence;

import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import com.business.slackservice.domain.slackTemplate.repository.SlackTemplateRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SlackTemplateJpaRepository extends JpaRepository<SlackTemplateEntity, UUID>,
    SlackTemplateRepository, QuerydslPredicateExecutor<SlackTemplateEntity> {

}
