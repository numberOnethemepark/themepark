package com.business.themeparkservice.themepark.domain.entity;

import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPutDTOApiV1;
import com.business.themeparkservice.themepark.domain.vo.ThemeparkType;
import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_themeparks")
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "themepark",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ThemeparkHashtagEntity> themeparkHashtagEntityList;

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

    public void update(ReqThemeparkPutDTOApiV1 reqDto) {
        var dto = reqDto.getThemepark();

        Optional.ofNullable(dto.getName()).ifPresent(name -> this.name = name);
        Optional.ofNullable(dto.getDescription()).ifPresent(description -> this.description = description);
        Optional.ofNullable(dto.getType()).ifPresent(type -> this.type = type);
        Optional.ofNullable(dto.getOperationStartTime()).ifPresent(time -> this.operationStartTime = time);
        Optional.ofNullable(dto.getOperationEndTime()).ifPresent(time -> this.operationEndTime = time);
        Optional.ofNullable(dto.getHeightLimit()).ifPresent(limit -> this.heightLimit = limit);
        Optional.ofNullable(dto.getSupervisor()).ifPresent(supervisor -> this.supervisor = supervisor);
    }

}
