package com.business.themeparkservice.waiting.domain.entity;

import com.business.themeparkservice.waiting.domain.vo.WaitingStatus;
import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "p_waitings")
@Getter
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
    @DefaultValue("WAITING")
    private WaitingStatus waitingStatus;


}
