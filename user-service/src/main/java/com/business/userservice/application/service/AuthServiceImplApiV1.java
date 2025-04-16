package com.business.userservice.application.service;

import com.business.userservice.application.dto.request.ReqAuthPostJoinDTOApiV1;
import com.business.userservice.application.dto.request.ReqAuthPostJoinDTOApiV1.User;
import com.business.userservice.application.dto.response.ResAuthPostJoinDTOApiV1;
import com.business.userservice.domain.user.entity.UserEntity;
import com.business.userservice.domain.user.repository.UserRepository;
import com.business.userservice.domain.user.vo.RoleType;
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

    private void validateDuplicateUser(String username, String slackId) {
        userRepository.findByUsername(username)
            .ifPresent(userEntity -> {
                throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
            });
        userRepository.findBySlackId(slackId)
            .ifPresent(userEntity -> {
                throw new IllegalArgumentException("이미 존재하는 Slack ID입니다.");
            });
    }
}
