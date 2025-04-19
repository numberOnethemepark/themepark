package com.business.slackservice.domain.slack.entity;

import com.business.slackservice.domain.slack.vo.EventType;
import com.business.slackservice.domain.slack.vo.SlackStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_slacks")
@Entity
public class SlackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "slack_id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @Column(name = "trigger_id", nullable = false)
    private UUID triggerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SlackStatus status;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Embedded
    private SlackTarget target;
}
