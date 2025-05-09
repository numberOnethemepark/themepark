package com.business.userservice.domain.user.repository;

import java.sql.Timestamp;
import java.util.Optional;

public interface BlacklistRepository {
    void save(String userId);
    Optional<Timestamp> findByUserId(String userId);
    void delete(String userId);
}
