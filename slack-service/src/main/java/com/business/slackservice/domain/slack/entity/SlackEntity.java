package com.business.slackservice.domain.slack.entity;

import com.business.slackservice.domain.slack.vo.SlackStatus;
import com.business.slackservice.domain.slack.vo.TargetType;
import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_slacks")
@Entity
public class SlackEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "slack_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slack_event_type")
    private SlackEventTypeEntity slackEventType;

    @Column(name = "related_name", nullable = false)
    private String relatedName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SlackStatus status;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Embedded
    private SlackTarget target;

    @Builder
    private SlackEntity (
        SlackEventTypeEntity slackEventType,
        String relatedName,
        SlackStatus status,
        String content,
        SlackTarget target
    ) {
        this.slackEventType = slackEventType;
        this.relatedName = relatedName;
        this.status = status;
        this.content = content;
        this.target = target;
    }

    public static SlackEntity create(
        SlackEventTypeEntity slackEventTypeEntity,
        String relatedName,
        SlackStatus status,
        String content,
        String slackId,
        TargetType target
    ) {
        return SlackEntity.builder()
            .slackEventType(slackEventTypeEntity)
            .relatedName(relatedName)
            .status(status)
            .content(content)
            .target(SlackTarget.of(slackId, target))
            .build();
    }
}
