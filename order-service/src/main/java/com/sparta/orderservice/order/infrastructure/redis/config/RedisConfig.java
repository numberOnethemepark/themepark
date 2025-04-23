package com.sparta.orderservice.order.infrastructure.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.orderservice.order.application.dto.reponse.ResProductGetByIdDTOApiV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ResProductGetByIdDTOApiV1> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // Redis 와 통신을 위한 template 설정과정 Key = String, Value = ResProductGetByIdDTOApiV1 객체를 사용
        RedisTemplate<String, ResProductGetByIdDTOApiV1> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // JAVA -> JSON 객체로 바꿔주는 / JSON -> JAVA
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // LocalDateTime 시간데이터를 JSON 으로 변환하기 위한 도구
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 시간데이터를 Long 타입이 아닌 String 타입
        // Jackson 은 public 이나, getter 메서드만 직렬화 시킴, private 필드도 직렬화를 시켜주기위해 등록
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 직렬화 도구
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        // Key 는 문자열 기대로 사용, Value 는 JSON 으로 변환해서 저장
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);

        // 만든도구 초기화 후 반환
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}