package com.business.gatewayservice.infrastructure.jwt;

import com.business.gatewayservice.application.exception.GatewayExceptionCode;
import com.github.themepark.common.application.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;

    public JwtTokenProvider(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public void validateToken(String token) {
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

    public String getUserIdFromToken(String jwtToken) {
        return parseClaims(jwtToken).get("userId", String.class);
    }

    public String getRoleFromToken(String jwtToken) {
        return parseClaims(jwtToken).get("role", String.class);
    }

    public String getSlackIdFromToken(String jwtToken) {
        return parseClaims(jwtToken).get("slackId", String.class);
    }

    public Date getIssuedAtFromToken(String jwtToken) {
        return parseClaims(jwtToken).getIssuedAt();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}
