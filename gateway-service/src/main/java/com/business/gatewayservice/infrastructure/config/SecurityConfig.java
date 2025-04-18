package com.business.gatewayservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.applyPermitDefaultValues();
                    config.addAllowedOrigin("http://localhost:8002"); // 허용할 출처를 명시
                    config.addAllowedMethod(HttpMethod.GET); // GET 요청 허용
                    config.addAllowedMethod(HttpMethod.POST); // POST 요청 허용
                    config.addAllowedMethod(HttpMethod.PUT); // PUT 요청 허용
                    config.addAllowedHeader("*"); // 모든 헤더 허용
                    return config;
                }))
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                .anyExchange().permitAll() // 모든 요청 허용
            );

        return http.build();
    }
}