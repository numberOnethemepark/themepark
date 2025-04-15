package com.business.themeparkservice.waiting.domain.entity;

import com.business.themeparkservice.waiting.domain.vo.WaitingStatus;
import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "p_waitings")
@SQLRestriction("waiting_status= 'WAITING' AND deleted_at IS NULL")
@Getter
@NoArgsConstructor
public class WaitingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "waiting_id",nullable = false)
    private UUID id;

    @Column(name = "user_id",nullable = false)
    private Integer userId;

    @Column(name = "themepark_id",nullable = false)
    private UUID themeparkId;

    @Column(name = "waiting_number",nullable = false)
    private Integer waitingNumber;

    @Column(name = "waiting_left",nullable = false)
    private Integer waitingLeft;

    @Column(name = "waiting_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private WaitingStatus waitingStatus = WaitingStatus.WAITING;

    @Builder
    public WaitingEntity(Integer userId, UUID themeparkId, Integer waitingNumber, Integer waitingLeft) {
        this.userId = userId;
        this.themeparkId = themeparkId;
        this.waitingNumber = waitingNumber;
        this.waitingLeft = waitingLeft;
    }

    public void updateWaitingLeft(int waitingLeft) {
        this.waitingLeft = waitingLeft;
    }
}
