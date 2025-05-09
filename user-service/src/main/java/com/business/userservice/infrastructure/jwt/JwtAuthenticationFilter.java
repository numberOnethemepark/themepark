package com.business.userservice.infrastructure.jwt;

import com.business.userservice.application.dto.request.ReqAuthPostLoginDTOApiV1;
import com.business.userservice.application.dto.response.ResAuthPostLoginDTOApiV1;
import com.business.userservice.application.exception.AuthExceptionCode;
import com.business.userservice.infrastructure.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.themepark.common.application.exception.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/v1/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {

        String username;
        String password;

        if (isJsonRequest(request)) {
            // JSON 요청 처리
            try {
                ReqAuthPostLoginDTOApiV1 reqDto = new ObjectMapper().readValue(request.getInputStream(), ReqAuthPostLoginDTOApiV1.class);
                username = reqDto.getUser().getUsername();
                password = reqDto.getUser().getPassword();
            } catch (IOException e) {
                log.error("요청 바디 파싱 실패: {}", e.getMessage());
                throw new CustomException(AuthExceptionCode.INVALID_REQUEST_BODY);
            }
        } else {
            // Form 요청 처리
            username = request.getParameter("username");
            password = request.getParameter("password");
        }

        validateUsernameAndPassword(username, password);

        return getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
    }

    @Override
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        super.setRequiresAuthenticationRequestMatcher(
            new AntPathRequestMatcher(filterProcessesUrl, "POST"));
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult
    ) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        String accessToken = jwtUtil.createAccessToken(userDetails.getId(), userDetails.getRole(), userDetails.getSlackId());
        String refreshToken = jwtUtil.createRefreshToken(accessToken);

        sendSuccessResponse(response, accessToken, refreshToken);
    }

    @Override
    protected void unsuccessfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException failed
    ) {
        throw new CustomException(AuthExceptionCode.AUTHENTICATION_FAILED);
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 14);    // 2주
        return cookie;
    }

    private boolean isJsonRequest(HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().contains("application/json");
    }

    private void validateUsernameAndPassword(String username, String password) {
        if (username == null || password == null) {
            throw new CustomException(AuthExceptionCode.MISSING_USERNAME_OR_PASSWORD);
        }
    }

    private void sendSuccessResponse(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setHeader("ACCESS_TOKEN", accessToken);
        response.addCookie(createCookie("refresh", refreshToken));

        ResAuthPostLoginDTOApiV1 dto = ResAuthPostLoginDTOApiV1.of(accessToken, refreshToken);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().write(objectMapper.writeValueAsString(dto));
        } catch (IOException e) {
            log.error("응답 쓰기 실패: {}", e.getMessage());
            throw new CustomException(AuthExceptionCode.RESPONSE_WRITE_FAIL);
        }
    }
}
