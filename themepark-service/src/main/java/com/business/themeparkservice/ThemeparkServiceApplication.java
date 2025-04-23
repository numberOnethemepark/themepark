package com.business.themeparkservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;

@EnableAspectJAutoProxy
@EnableRetry
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.business.themeparkservice","com.github.themepark.common"})
public class ThemeparkServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemeparkServiceApplication.class, args);
    }

}
