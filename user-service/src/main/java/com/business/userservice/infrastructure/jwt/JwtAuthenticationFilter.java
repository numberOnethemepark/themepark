package com.business.userservice.infrastructure.jwt;

import com.business.userservice.application.dto.request.ReqAuthPostLoginDTOApiV1;
import com.business.userservice.application.dto.response.ResAuthPostLoginDTOApiV1;
import com.business.userservice.application.exception.AuthExceptionCode;
import com.business.userservice.domain.user.vo.RoleType;
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
    private final JwtProperties jwtProperties;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, JwtProperties jwtProperties) {
        this.jwtUtil = jwtUtil;
        this.jwtProperties = jwtProperties;
        setFilterProcessesUrl("/v1/auth/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        String username;
        String password;

        if (request.getContentType() != null && request.getContentType().contains("application/json")) {
            // JSON 요청 처리
            try {
                ReqAuthPostLoginDTOApiV1 reqDto = new ObjectMapper().readValue(request.getInputStream(), ReqAuthPostLoginDTOApiV1.class);
                username = reqDto.getUser().getUsername();
                password = reqDto.getUser().getPassword();
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomException(AuthExceptionCode.INVALID_REQUEST_BODY);
            }
        } else {
            // Form 요청 처리
            username = request.getParameter("username");
            password = request.getParameter("password");
        }

        if (username == null || password == null) {
            throw new CustomException(AuthExceptionCode.MISSING_USERNAME_OR_PASSWORD);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return getAuthenticationManager().authenticate(authToken);
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

        Long userId = userDetails.getId();
        RoleType role = userDetails.getRole();

        String accessToken = jwtUtil.createAccessToken(userId, role);
        String refreshToken = jwtUtil.createRefreshToken(accessToken);

        ResAuthPostLoginDTOApiV1 dto = ResAuthPostLoginDTOApiV1.of(accessToken, refreshToken);

        response.setHeader("ACCESS_TOKEN", accessToken);

        Cookie refreshCookie = createCookie("refresh", refreshToken);
        response.addCookie(refreshCookie);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        try {
            response.getWriter().write(mapper.writeValueAsString(dto));
        } catch (IOException e) {
            throw new CustomException(AuthExceptionCode.RESPONSE_WRITE_FAIL);
        }
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
}
