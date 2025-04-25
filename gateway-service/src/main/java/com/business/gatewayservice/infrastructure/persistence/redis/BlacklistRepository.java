package com.business.gatewayservice.infrastructure.persistence.redis;

import java.sql.Timestamp;
import java.util.Optional;

public interface BlacklistRepository {
    Optional<Timestamp> findByUserId(String userId);
}
