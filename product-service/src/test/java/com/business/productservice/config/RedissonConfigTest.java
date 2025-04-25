package com.business.productservice.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedissonConfigTest {
    @Autowired
    private RedissonClient redissonClient;

    @Test
    void redissonClientTest() {
        long keyCount = redissonClient.getKeys().count();
        System.out.println("현재 Redis 키 개수: " + keyCount);
        Assertions.assertThat(keyCount).isGreaterThanOrEqualTo(0); // 연결 테스트용
    }
}
