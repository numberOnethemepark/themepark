package com.business.gatewayservice.infrastructure.persistence.redis;

import java.sql.Timestamp;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlacklistRepositoryImpl implements BlacklistRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private final String BLACKLIST_PREFIX = "blacklist:";

    @Override
    public Optional<Timestamp> findByUserId(String userId) {
        return Optional.ofNullable((Timestamp) redisTemplate.opsForValue().get(BLACKLIST_PREFIX + userId));
    }
}
