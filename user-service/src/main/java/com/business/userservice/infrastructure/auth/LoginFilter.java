package com.business.userservice.infrastructure.auth;

import com.business.userservice.application.dto.request.ReqAuthPostLoginDTOApiV1;
import com.business.userservice.application.dto.response.ResAuthPostLoginDTOApiV1;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public LoginFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/v1/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        String username;
        String password;

        if (request.getContentType() != null && request.getContentType()
            .contains("application/json")) {
            // JSON 요청 처리
            log.info("JSON 요청 처리");
            try {
                ObjectMapper mapper = new ObjectMapper();
                ReqAuthPostLoginDTOApiV1 dto = mapper.readValue(request.getInputStream(),
                    ReqAuthPostLoginDTOApiV1.class);
                username = dto.getUser().getUsername();
                password = dto.getUser().getPassword();
                log.info("username: {}, password: {}", username, password);
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse JSON request", e);
            }
        } else {
            // Form 요청 처리
            username = request.getParameter("username");
            password = request.getParameter("password");
        }

        if (username == null || password == null) {
            throw new RuntimeException("Username or Password is missing");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            username, password);
        log.info("authToken: {}", authToken);
        return this.getAuthenticationManager().authenticate(authToken);
    }

    @Override
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        super.setFilterProcessesUrl(filterProcessesUrl);
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult
    ) throws IOException, ServletException {
        log.info("successfulAuthentication");
        String username = authResult.getName();
        log.info("username: {}", username);

        UserDetailsImpl details = (UserDetailsImpl) authResult.getPrincipal();

        String access = jwtUtil.createAccessToken(
            details.getId(),
            details.getUsername(),
            details.getAuthorities().toString(),
            details.getIsBlacklisted()
        );
        String refresh = jwtUtil.createRefreshToken(
            details.getId(),
            details.getUsername(),
            details.getAuthorities().toString(),
            details.getIsBlacklisted()
        );
        ResAuthPostLoginDTOApiV1 dto = ResAuthPostLoginDTOApiV1.of(access, refresh);

        response.setHeader("access", dto.getAccessJwt());

        Cookie refreshCookie = createCookie("refresh", dto.getRefreshJwt());
        response.addCookie(refreshCookie);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(dto));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed)
        throws IOException, ServletException {
        log.info("unsuccessfulAuthentication!!!");
        log.info("AuthException class: {}", failed.getClass().getSimpleName());
        log.info("AuthException message: {}", failed.getMessage());
        super.unsuccessfulAuthentication(request, response, failed);
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 14);    // 2주
        return cookie;
    }
}
