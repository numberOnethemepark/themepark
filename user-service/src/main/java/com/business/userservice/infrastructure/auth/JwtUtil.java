package com.business.userservice.infrastructure.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

    private SecretKey secretKey;
    private final long accessTokenExpiration = 1000L * 60 * 60 * 24;
    private final long refreshTokenExpiration = 1000L * 60 * 60 * 24 * 12;
    private final String BEARER_PREFIX = "Bearer ";

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
            SIG.HS256.key().build().getAlgorithm());
    }

    public String createAccessToken(Long id, String username, String role, Boolean isBlacklisted) {
        String token = createToken("ACCESS", id, username, role, isBlacklisted,
            accessTokenExpiration);
        return addBearerPrefix(token);
    }

    public String createRefreshToken(Long id, String username, String role, Boolean isBlacklisted) {
        return createToken("REFRESH", id, username, role, isBlacklisted, refreshTokenExpiration);
    }

    public String getId(String token) {
        return getClaimFromToken(removeBearerPrefix(token), Claims::getSubject);
    }

    public String getUsername(String token) {
        return getClaimFromToken(removeBearerPrefix(token),
            claims -> claims.get("username", String.class));
    }

    public String getRole(String token) {
        return getClaimFromToken(removeBearerPrefix(token),
            claims -> claims.get("role", String.class));
    }

    public Boolean getIsBlacklisted(String token) {
        return getClaimFromToken(removeBearerPrefix(token),
            claims -> claims.get("isBlacklisted", Boolean.class));
    }

    public String getCategory(String token) {
        return getClaimFromToken(removeBearerPrefix(token),
            claims -> claims.get("category", String.class));
    }

    public Boolean isExpired(String token) {
        return getClaimFromToken(token, Claims::getExpiration).before(new Date());
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(removeBearerPrefix(token))
            .getBody();
        return claimsResolver.apply(claims);
    }

    public String createToken(String category, Long id, String username, String role,
        Boolean isBlacklisted, Long expiresIn) {
        return Jwts.builder()
            .subject(id.toString())
            .claim("category", category)
            .claim("username", username)
            .claim("role", role)
            .claim("isBlacklisted", isBlacklisted)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiresIn))
            .signWith(secretKey)
            .compact();
    }

    private String addBearerPrefix(String token) {
        return BEARER_PREFIX + token;
    }

    private String removeBearerPrefix(String token) {
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return token;
    }
}
