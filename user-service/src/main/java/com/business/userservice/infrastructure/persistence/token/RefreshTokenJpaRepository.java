package com.business.userservice.infrastructure.persistence.token;

import com.business.userservice.domain.user.entity.RefreshTokenEntity;
import com.business.userservice.domain.user.repository.RefreshTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends RefreshTokenRepository,
    JpaRepository<RefreshTokenEntity, String> {

}
