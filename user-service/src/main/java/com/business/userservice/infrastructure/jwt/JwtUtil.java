package com.business.userservice.infrastructure.jwt;


import com.business.userservice.application.exception.AuthExceptionCode;
import com.business.userservice.domain.user.vo.RoleType;
import com.business.userservice.domain.user.vo.TokenExpiration;
import com.business.userservice.infrastructure.security.UserDetailsImpl;
import com.business.userservice.infrastructure.security.UserDetailsServiceImpl;
import com.github.themepark.common.application.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    public static final String BEARER_PREFIX = "Bearer ";

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public JwtUtil(
        UserDetailsServiceImpl userDetailsServiceImpl
    ) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    public String createAccessToken(Long userId, RoleType role, String slackId) {
        String token = createToken("ACCESS", userId, role, slackId,
            TokenExpiration.ACCESS_TOKEN.getSeconds());
        return addBearerPrefix(token);
    }

    public String createRefreshToken(String accessToken) {
        Claims claims = getClaimsFromToken(removeBearerPrefix(accessToken));
        Long userId = Long.parseLong(claims.getSubject());
        UserDetailsImpl user = (UserDetailsImpl) userDetailsServiceImpl.loadUserById(userId);

        return createToken("REFRESH", userId, user.getRole(), user.getSlackId(),
            TokenExpiration.REFRESH_TOKEN.getSeconds());
    }

    public String validateRefreshToken(String accessToken, String refreshToken) {
        String accessTokenSubject = extractSubject(accessToken);
        Claims refreshTokenClaims = getClaimsFromToken(refreshToken);

        if (!accessTokenSubject.equals(refreshTokenClaims.getSubject())) {
            return HttpStatus.UNAUTHORIZED.toString();
        }

        if (isTokenExpired(refreshTokenClaims.getExpiration())) {
            throw new CustomException(AuthExceptionCode.REFRESH_TOKEN_EXPIRED);
        }

        UserDetailsImpl user = (UserDetailsImpl) userDetailsServiceImpl.loadUserById(
            Long.parseLong(accessTokenSubject));
        return createAccessToken(user.getId(), user.getRole(), user.getSlackId());
    }

    public String getUserIdFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public Date getIatFromToken(String token) {
        return getClaimsFromToken(token).getIssuedAt();
    }

    //accessToken 생성
    public String createToken(String category, Long userId, RoleType role, String slackId,
        Long expiresIn) {
        return Jwts.builder()
            .setSubject(userId.toString())
            .claim("category", category)
            .claim("userId", userId.toString())
            .claim("role", role)
            .claim("slackId", slackId)
            .setExpiration(getExpiryDateFromNow(expiresIn))
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .signWith(getSecretKey(), signatureAlgorithm)
            .compact();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String addBearerPrefix(String token) {
        return BEARER_PREFIX + token;
    }

    private Date getExpiryDateFromNow(Long expiresIn) {
        return new Date(System.currentTimeMillis() + expiresIn);
    }

    private String removeBearerPrefix(String token) {
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return token;
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private String extractSubject(String token) {
        try {
            return getClaimsFromToken(token).getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    private boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}