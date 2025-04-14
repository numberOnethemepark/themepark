package com.business.userservice.infrastructure.config;

import com.business.userservice.infrastructure.auth.JwtFilter;
import com.business.userservice.infrastructure.auth.JwtUtil;
import com.business.userservice.infrastructure.auth.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        LoginFilter loginFilter = new LoginFilter(jwtUtil);
        loginFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        loginFilter.setFilterProcessesUrl("/v1/auth/login");

        http
            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/v1/auth/join", "/v1/auth/login", "/swagger-ui/index.html").permitAll()
//                .anyRequest().authenticated()
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable())   // CSRF 보호 비활성화
            .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)
            .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();


    }
}
