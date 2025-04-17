package com.business.userservice.application.service;

import com.business.userservice.application.dto.request.ReqAuthPostJoinDTOApiV1;
import com.business.userservice.application.dto.request.ReqAuthPostJoinDTOApiV1.User;
import com.business.userservice.application.dto.response.ResAuthPostJoinDTOApiV1;
import com.business.userservice.application.exception.AuthExceptionCode;
import com.business.userservice.domain.user.entity.UserEntity;
import com.business.userservice.domain.user.repository.UserRepository;
import com.business.userservice.domain.user.vo.RoleType;
import com.business.userservice.infrastructure.jwt.JwtUtil;
import com.github.themepark.common.application.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImplApiV1 implements AuthServiceApiV1 {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public ResAuthPostJoinDTOApiV1 joinBy(ReqAuthPostJoinDTOApiV1 dto) {
        User user = dto.getUser();

        validateDuplicateUser(user.getUsername(), user.getSlackId());

        UserEntity saveUser = UserEntity.of(
            user.getUsername(),
            passwordEncoder.encode(user.getPassword()),
            user.getSlackId(),
            RoleType.USER
        );

        UserEntity savedUser = userRepository.save(saveUser);
        return ResAuthPostJoinDTOApiV1.of(savedUser);
    }

    @Override
    public String refreshToken(String accessToken, String refreshToken) {
        return jwtUtil.validateRefreshToken(accessToken, refreshToken);
    }

    private void validateDuplicateUser(String username, String slackId) {
        userRepository.findByUsername(username)
            .ifPresent(userEntity -> {
                throw new CustomException(AuthExceptionCode.DUPLICATE_USERNAME);
            });
        userRepository.findBySlackId(slackId)
            .ifPresent(userEntity -> {
                throw new CustomException(AuthExceptionCode.DUPLICATE_SLACK_ID);
            });
    }
}
