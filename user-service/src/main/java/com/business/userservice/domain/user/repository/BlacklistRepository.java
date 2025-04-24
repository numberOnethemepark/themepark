package com.business.userservice.domain.user.repository;

import java.util.Optional;

public interface BlacklistRepository {
    void save(String userId);
    Optional<String> findByUserId(String userId);
    void delete(String userId);
}
