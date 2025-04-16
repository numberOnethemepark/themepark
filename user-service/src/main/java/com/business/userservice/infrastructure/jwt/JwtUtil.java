package com.business.userservice.infrastructure.jwt;


import com.business.userservice.application.exception.AuthExceptionCode;
import com.business.userservice.domain.user.entity.RefreshTokenEntity;
import com.business.userservice.domain.user.repository.RefreshTokenRepository;
import com.business.userservice.domain.user.vo.RoleType;
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
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

    public static final String BEARER_PREFIX = "Bearer ";

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final JwtProperties jwtProperties;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtUtil(
        JwtProperties jwtProperties,
        UserDetailsServiceImpl userDetailsServiceImpl,
        RefreshTokenRepository refreshTokenRepository
    ) {
        this.jwtProperties = jwtProperties;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createAccessToken(Long userId, RoleType role) {
        String token = createToken("ACCESS", userId, role,
            jwtProperties.getAccessTokenExpiration());
        return addBearerPrefix(token);
    }

    public String createRefreshToken(String accessToken) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(removeBearerPrefix(accessToken))
            .getBody();

        Long userId = Long.parseLong(claims.getSubject());
        UserDetailsImpl user = (UserDetailsImpl) userDetailsServiceImpl.loadUserById(userId);
        Optional<RefreshTokenEntity> refreshTokenByUserId = refreshTokenRepository.findByUserId(
            user.getId());

        // refresh token이 이미 존재할 경우 해당 refresh token 반환
        if (refreshTokenByUserId.isPresent()) {
            return refreshTokenByUserId.get().getRefreshToken();
        }

        // refresh token이 존재하지 않을 경우 refresh token 생성
        String refreshToken = createToken("REFRESH", userId, user.getRole(),
            jwtProperties.getRefreshTokenExpiration());
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.of(
            userId,
            refreshToken,
            getExpiryDateFromNow(jwtProperties.getRefreshTokenExpiration())
        );
        refreshTokenRepository.save(refreshTokenEntity);

        return refreshToken;
    }

    public String validateRefreshToken(String accessToken, String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByRefreshToken(
                refreshToken)
            .orElseThrow(() -> new CustomException(AuthExceptionCode.REFRESH_TOKEN_NOT_FOUND));

        String subject;

        // 만료된 access token 에서 subject 추출을 위한 클레임 파싱
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
            subject = claims.getSubject();
        } catch (ExpiredJwtException e) {
            subject = e.getClaims().getSubject();
        }

        // 만료된 accessToken 의 주인과 입력한 refreshToken 의 유저가 같은지 확인
        if (!subject.equals(refreshTokenEntity.getUserId().toString())) {
            throw new CustomException(AuthExceptionCode.REFRESH_TOKEN_USER_MISMATCH);
        }

        // refresh token이 유효하지 않은 경우 (만료기간이 지난 경우)
        if (refreshTokenEntity.getValidity().before(new Date())) {
            // RefreshToken DB에서 해당 엔티티 삭제 후 만료 response 반환
            refreshTokenRepository.delete(refreshTokenEntity);
            return HttpStatus.UNAUTHORIZED.toString();
        } else {
            // refresh token이 유효한 경우 => access token 발급
            UserDetailsImpl user = (UserDetailsImpl) userDetailsServiceImpl.loadUserById(
                Long.parseLong(subject));
            return createAccessToken(user.getId(), user.getRole());
        }
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
            .setExpiration(getExpiryDateFromNow(expiresIn))
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .signWith(getSecretKey(), signatureAlgorithm)
            .compact();
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
}