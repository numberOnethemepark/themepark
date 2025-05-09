package com.business.slackservice.domain.slackEventType.entity;

import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_slack_event_types")
@Entity
public class SlackEventTypeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "slack_event_type_id")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder
    private SlackEventTypeEntity(
        String name
    ) {
        this.name = name;
    }

    public static SlackEventTypeEntity create(
        String name
    ) {
        return SlackEventTypeEntity.builder()
            .name(name)
            .build();
    }

    public void update(String name) {
        this.name = name;
    }
}
