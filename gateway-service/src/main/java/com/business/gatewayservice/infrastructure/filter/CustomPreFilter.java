package com.business.gatewayservice.infrastructure.filter;

import com.business.gatewayservice.application.exception.GatewayExceptionCode;
import com.github.themepark.common.application.exception.CustomException;
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
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
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

    private final AntPathMatcher matcher = new AntPathMatcher();

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 인증을 적용하지 않을 경로 목록
    private static final Map<HttpMethod, List<String>> EXCLUDED_PATHS_BY_METHOD = Map.of(
        HttpMethod.GET, List.of(
            "/v1/products",
            "/v1/products/*",
            "/v1/themeparks/*",
            "/v1/hashtags/*",
            "/v1/auth/refresh",
            "/springdoc/openapi3-user-service.json"
        ),
        HttpMethod.POST, List.of(
            "/v1/auth/join",
            "/v1/auth/login"
        )
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        HttpMethod method = exchange.getRequest().getMethod();

        log.info("요청 경로 {}, 메소드 {}", path, method);

        if (isExcludedPath(path, method)) {
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
                    .header("X-User-SlackId", getSlackIdFromToken(jwtToken))
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
            throw new CustomException(GatewayExceptionCode.EXPIRED_JWT);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new CustomException(GatewayExceptionCode.INVALID_SIGNATURE);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new CustomException(GatewayExceptionCode.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new CustomException(GatewayExceptionCode.EMPTY_CLAIMS);
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
            throw new CustomException(GatewayExceptionCode.AUTH_HEADER_MISSING);
        }
        return authorizations.stream()
            .filter(this::isBearerType)
            .findFirst()
            .orElseThrow(() -> new CustomException(GatewayExceptionCode.BEARER_TOKEN_NOT_FOUND));
    }

    private boolean isExcludedPath(String path, HttpMethod method) {
        List<String> excludedPaths = EXCLUDED_PATHS_BY_METHOD.get(method);
        if (excludedPaths == null || excludedPaths.isEmpty()) {
            log.info("excludedPaths NULL");
            return false;
        }
        log.info("excludedPaths.stream().anyMatch(pattern -> matcher.match(pattern, path)): {}", excludedPaths.stream().anyMatch(pattern -> matcher.match(pattern, path)));
        return excludedPaths.stream().anyMatch(pattern -> matcher.match(pattern, path));
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

    private String getSlackIdFromToken(String jwtToken) {
        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(jwtToken)
            .getBody()
            .get("slackId", String.class);
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
