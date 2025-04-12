package com.business.themeparkservice.themepark.domain.entity;

import com.business.themeparkservice.themepark.domain.vo.ThemeparkType;
import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_themeparks")
public class ThemeparkEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "themepark_id", nullable = false)
    private UUID id;

    @Column(name = "themepark_name", nullable = false)
    private String name;

    @Column(name = "themepark_description", nullable = false)
    private String description;

    @Column(name = "themepark_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ThemeparkType type;

    @Column(name = "operation_start_time", nullable = false)
    private LocalTime operationStartTime;

    @Column(name = "operation_end_time", nullable = false)
    private LocalTime operationEndTime;

    @Column(name = "height_limit", nullable = false)
    @ColumnDefault("'없음'")
    private String heightLimit;

    @Column(name = "supervisor", nullable = false)
    @ColumnDefault("false")
    private boolean supervisor;

    @Builder
    public ThemeparkEntity(String name, String description, ThemeparkType type, LocalTime operationStartTime, LocalTime operationEndTime, String heightLimit, boolean supervisor) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.operationStartTime = operationStartTime;
        this.operationEndTime = operationEndTime;
        this.heightLimit = heightLimit;
        this.supervisor = supervisor;
    }

    @Builder
    public ThemeparkEntity() {}
}
