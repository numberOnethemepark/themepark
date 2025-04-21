package com.business.slackservice.domain.slackTemplate.entity;

import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_slack_templates")
@Entity
public class SlackTemplateEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "slack_template_id")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slack_event_type")
    private SlackEventTypeEntity slackEventTypeEntity;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    private SlackTemplateEntity(
        SlackEventTypeEntity eventType,
        String content
    ) {
        this.slackEventTypeEntity = eventType;
        this.content = content;
    }

    public static SlackTemplateEntity create(
        SlackEventTypeEntity eventType,
        String content
    ) {
        return SlackTemplateEntity.builder()
            .eventType(eventType)
            .content(content)
            .build();
    }

    public void update(SlackEventTypeEntity slackEventTypeEntity, String content) {
        if (slackEventTypeEntity != null) this.slackEventTypeEntity = slackEventTypeEntity;
        if (content != null) this.content = content;
    }
}
