package com.business.userservice.infrastructure.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "service.jwt")
public class JwtProperties {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private Long accessTokenExpiration = 1000L * 60 * 60 * 24;
    private Long refreshTokenExpiration = 1000L * 60 * 60 * 24 * 12;

}
