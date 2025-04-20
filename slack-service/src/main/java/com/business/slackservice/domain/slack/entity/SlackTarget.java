package com.business.slackservice.domain.slack.entity;

import com.business.slackservice.domain.slack.vo.TargetType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Embeddable
public class SlackTarget {
    @Column(name = "target_slack_id", nullable = false)
    private String slackId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private TargetType type;

    @Builder
    private SlackTarget (
        String slackId,
        TargetType type
    ) {
        this.slackId = slackId;
        this.type = type;
    }

    public static SlackTarget of (String slackId, TargetType type) {
        return SlackTarget.builder()
            .slackId(slackId)
            .type(type)
            .build();
    }
}
