package com.business.slackservice.infrastructure.persistence;

import com.business.slackservice.domain.slack.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slack.repository.SlackEventTypeRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackEventTypeJpaRepository extends JpaRepository<SlackEventTypeEntity, UUID>,
    SlackEventTypeRepository {

}
