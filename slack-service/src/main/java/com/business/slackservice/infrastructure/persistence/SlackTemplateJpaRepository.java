package com.business.slackservice.infrastructure.persistence;

import com.business.slackservice.domain.slack.entity.SlackEntity;
import com.business.slackservice.domain.slack.repository.SlackTemplateRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackTemplateJpaRepository extends JpaRepository<SlackEntity, UUID>,
    SlackTemplateRepository {

}
