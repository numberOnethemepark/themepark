package com.business.themeparkservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.retry.annotation.EnableRetry;

@EnableAspectJAutoProxy
@EnableRetry
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.business.themeparkservice","com.github.themepark.common"})
@EnableJpaRepositories(basePackages = "com.business.themeparkservice.**.infastructure.persistence.**")
@EnableRedisRepositories(basePackages = "com.business.themeparkservice.waiting.infastructure.redis")
public class ThemeparkServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemeparkServiceApplication.class, args);
    }

}
