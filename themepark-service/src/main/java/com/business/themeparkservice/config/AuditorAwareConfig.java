package com.business.themeparkservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditorAwareConfig {

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return () -> Optional.of(1L); // 항상 사용자 ID를 1로 설정
    }
}
