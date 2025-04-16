package com.business.gatewayservice.infrastructure.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "JWT 인가 필터")
public class CustomPreFilter implements GlobalFilter, Ordered {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private static final String BEARER_PREFIX = "Bearer ";


    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 인증을 적용하지 않을 경로 목록
    private static final List<String> EXCLUDED_PATHS = List.of(
        "/v1/auth/join",
        "/v1/auth/login"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        log.info("요청 경로 {}", path);

        if (isExcludedPath(path)) {
            return chain.filter(exchange);
        }

        try {
            //토큰가져오기
            String authorization = getJwtFromHeader(exchange);
            String jwtToken = parseAuthorizationToken(authorization);
            //검증
            validateToken(jwtToken);


            if (!StringUtils.hasText(jwtToken)) {
                throw new Exception("토큰 비어있음");
            }

            //유효기간확인
            if (isValidateExpire(jwtToken)) {
                throw new Exception("만료된 토큰");
            }

            //  JWT 검증 성공 후 요청 헤더에 사용자 정보 추가
            ServerWebExchange modifiedExchange = exchange.mutate()
                .request(exchange.getRequest().mutate()
                    .header("X-User-Id", getUserIdFromToken(jwtToken))
                    .header("X-User-Role", getRoleFromToken(jwtToken))
                    .build())
                .build();

            log.info("********** info " + modifiedExchange);

            return chain.filter(modifiedExchange);

        } catch (Exception e) {
            log.error("JWT 필터 처리 중 예외 발생: {}", e.getMessage(), e);
            return sendErrorResponse(exchange, 999, e);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private void validateToken(String token) {
        try {
            parseClaims(token);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new IllegalArgumentException("만료된 JWT token");
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new IllegalArgumentException("Invalid JWT signature");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new IllegalArgumentException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new IllegalArgumentException("JWT claims is empty");
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private String getJwtFromHeader(ServerWebExchange exchange) throws Exception {
        List<String> authorizations = exchange.getRequest().getHeaders()
            .get(HttpHeaders.AUTHORIZATION);
        if (authorizations == null || authorizations.isEmpty()) {
            throw new Exception("오류");
        }
        return authorizations.stream()
            .filter(this::isBearerType)
            .findFirst()
            .orElseThrow(() -> new Exception("오류"));
    }

    private boolean isExcludedPath(String path) {
        return EXCLUDED_PATHS.stream().anyMatch(path::contains);
    }

    private String getUserIdFromToken(String jwtToken) {
        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(jwtToken)
            .getBody()
            .get("userId", String.class);
    }

    private String getRoleFromToken(String jwtToken) {
        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(jwtToken)
            .getBody()
            .get("role", String.class);
    }

    private Mono<Void> sendErrorResponse(ServerWebExchange exchange, int errorCode, Exception e) {
        String errorBody = "{\"code\": " + errorCode + ", \"message\": \"" + e.getMessage() + "\"}";

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer buffer = response.bufferFactory()
            .wrap(errorBody.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }


    private String parseAuthorizationToken(String authorization) {
        return authorization.replace(BEARER_PREFIX, "").trim();
    }

    private boolean isBearerType(String authorization) {
        return authorization.startsWith(BEARER_PREFIX);
    }

    private boolean isValidateExpire(String jwtToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(jwtToken)
            .getBody()
            .getExpiration();
        return expiration.before(new Date());
    }
}
