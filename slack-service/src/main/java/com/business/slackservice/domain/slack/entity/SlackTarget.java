package com.business.slackservice.domain.slack.entity;

import com.business.slackservice.domain.slack.vo.TargetType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class SlackTarget {
    @Column(name = "target_slack_id", nullable = false)
    private String slackId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private TargetType type;
}
