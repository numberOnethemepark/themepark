package com.business.slackservice.domain.slackTemplate.repository;

import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;

public interface SlackTemplateRepository {

    SlackTemplateEntity save(SlackTemplateEntity slackTemplate);
}

