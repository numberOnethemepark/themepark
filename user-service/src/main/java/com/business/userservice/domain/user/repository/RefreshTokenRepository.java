package com.business.userservice.domain.user.repository;

import com.business.userservice.domain.user.entity.RefreshTokenEntity;
import java.util.Optional;

public interface RefreshTokenRepository {
    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
    Optional<RefreshTokenEntity> findByUserId(Long id);
    RefreshTokenEntity save(RefreshTokenEntity refreshTokenEntity);
    void delete(RefreshTokenEntity refreshTokenEntity);
}
