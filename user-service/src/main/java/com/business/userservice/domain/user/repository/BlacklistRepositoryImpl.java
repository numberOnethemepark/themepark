package com.business.userservice.domain.user.repository;

import com.business.userservice.domain.user.vo.TokenExpiration;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BlacklistRepositoryImpl implements BlacklistRepository {
    private final String BLACKLIST_PREFIX = "blacklist:";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(String userId) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + userId, now, TokenExpiration.REFRESH_TOKEN.getDays(), TimeUnit.DAYS);
    }

    @Override
    public Optional<String> findByUserId(String userId) {
        return Optional.ofNullable((String) redisTemplate.opsForValue().get(BLACKLIST_PREFIX + userId));
    }

    @Override
    public void delete(String userId) {
        redisTemplate.delete(BLACKLIST_PREFIX + userId);
    }
}
