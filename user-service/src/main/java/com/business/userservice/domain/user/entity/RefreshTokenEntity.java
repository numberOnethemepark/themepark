package com.business.userservice.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_refresh_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "validity", nullable = false)
    private Date validity;

    @Builder
    private RefreshTokenEntity(Long userId, String refreshToken, Date validity) {
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.validity = validity;
    }

    public static RefreshTokenEntity of(Long userId, String refreshToken, Date validity) {
        return RefreshTokenEntity.builder()
            .userId(userId)
            .refreshToken(refreshToken)
            .validity(validity)
            .build();
    }
}
