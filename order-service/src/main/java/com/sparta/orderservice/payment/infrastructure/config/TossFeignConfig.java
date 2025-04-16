package com.sparta.orderservice.payment.infrastructure.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration(proxyBeanMethods = false)
public class TossFeignConfig {

    @Value("${toss.secretKey}")
    private String secretKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String encodedAuth = Base64.getEncoder().encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
            requestTemplate.header("Authorization", "Basic " + encodedAuth);
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}