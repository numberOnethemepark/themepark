package com.business.themeparkservice.themepark.domain.entity;

import com.business.themeparkservice.themepark.domain.vo.ThemeparkType;
import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
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
    private ThemeparkType type;

    @Column(name = "operation_start_time", nullable = false)
    private LocalTime operationStartTime;

    @Column(name = "operation_end_time", nullable = false)
    private LocalTime operationEndTime;

    @Column(name = "height_limit", nullable = false)
    private String heightLimit;

    @Column(name = "supervisor", nullable = false)
    @ColumnDefault("false")
    private boolean supervisor;

}
