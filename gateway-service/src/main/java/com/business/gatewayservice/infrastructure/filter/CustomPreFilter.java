package com.business.gatewayservice.infrastructure.filter;

import com.business.gatewayservice.application.exception.GatewayExceptionCode;
import com.business.gatewayservice.infrastructure.jwt.JwtTokenProvider;
import com.business.gatewayservice.infrastructure.persistence.redis.BlacklistRepository;
import com.github.themepark.common.application.exception.CustomException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;
    private final BlacklistRepository blacklistRepository;
    private final AntPathMatcher matcher = new AntPathMatcher();

    // JWT 인증을 적용하지 않을 경로 목록
    private static final Map<HttpMethod, List<String>> EXCLUDED_PATHS_BY_METHOD = Map.of(
        HttpMethod.GET, List.of(
            "/v?/products",
            "/v?/products*",
            "/v?/themeparks*",
            "/v?/hashtags*",
            "/v?/auth/refresh",
            "/springdoc/*"
        ),
        HttpMethod.POST, List.of(
            "/v?/auth/join",
            "/v?/auth/login"
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
            // 토큰가져오기
            String jwtToken = parseAuthorizationToken(getJwtFromHeader(exchange));
            // 검증
            jwtTokenProvider.validateToken(jwtToken);
            if (!StringUtils.hasText(jwtToken)) {
                throw new CustomException(GatewayExceptionCode.EMPTY_JWT_TOKEN);
            }
            // 차단 확인
            if (isBlacklisted(jwtToken)) {
                throw new CustomException(GatewayExceptionCode.BLOCKED_USER);
            }
            //  JWT 검증 성공 후 요청 헤더에 사용자 정보 추가
            ServerWebExchange modifiedExchange = exchange.mutate()
                .request(exchange.getRequest().mutate()
                    .header("X-User-Id", jwtTokenProvider.getUserIdFromToken(jwtToken))
                    .header("X-User-Role", jwtTokenProvider.getRoleFromToken(jwtToken))
                    .header("X-User-Slack-Id", jwtTokenProvider.getSlackIdFromToken(jwtToken))
                    .build())
                .build();

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

    private String getJwtFromHeader(ServerWebExchange exchange) throws Exception {
        return Optional.ofNullable(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
            .filter(this::isBearerToken)
            .map(token -> token.replace(BEARER_PREFIX, "").trim())
            .orElseThrow(() -> new CustomException(GatewayExceptionCode.BEARER_TOKEN_NOT_FOUND));
    }

    private boolean isExcludedPath(String path, HttpMethod method) {
        return Optional.ofNullable(EXCLUDED_PATHS_BY_METHOD.get(method))
            .orElse(List.of())
            .stream()
            .anyMatch(pattern -> matcher.match(pattern, path));
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

    private boolean isBearerToken(String token) {
        return token.startsWith(BEARER_PREFIX);
    }

    private boolean isBlacklisted(String token) {
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        Date issuedAt = jwtTokenProvider.getIssuedAtFromToken(token);

        return blacklistRepository.findByUserId(userId)
            .map(issuedAt::before)
            .orElse(false);
    }
}
