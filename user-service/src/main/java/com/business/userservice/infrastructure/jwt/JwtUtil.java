package com.business.userservice.infrastructure.jwt;


import com.business.userservice.domain.user.vo.RoleType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {
    public static final String BEARER_PREFIX = "Bearer ";

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {

        this.jwtProperties = jwtProperties;

    }

    public String createAccessToken(Long userId, RoleType role) {
        String token = createToken("ACCESS", userId, role, jwtProperties.getAccessTokenExpiration());
        return addBearerPrefix(token);
    }

    public String createRefreshToken(Long userId, RoleType role) {
        return createToken("REFRESH", userId, role, jwtProperties.getRefreshTokenExpiration());
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //accessToken 생성
    public String createToken(String category, Long userId, RoleType role, Long expiresIn) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("category", category)
                .claim("userId", userId.toString())
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSecretKey(), signatureAlgorithm)
                .compact();
    }

    private String addBearerPrefix(String token) {
        return BEARER_PREFIX + token;
    }
}