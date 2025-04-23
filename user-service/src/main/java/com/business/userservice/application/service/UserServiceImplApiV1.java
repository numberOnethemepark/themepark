package com.business.userservice.application.service;

import com.business.userservice.application.dto.request.ReqUserPostDeleteDTOApiV1;
import com.business.userservice.application.dto.request.ReqUserPutDTOApiV1;
import com.business.userservice.application.dto.response.ResAuthPutDTOApiV1;
import com.business.userservice.application.dto.response.ResUserGetByIdDTOApiV1;
import com.business.userservice.application.dto.response.ResUserGetDTOApiV1;
import com.business.userservice.application.exception.UserExceptionCode;
import com.business.userservice.domain.user.entity.UserEntity;
import com.business.userservice.domain.user.repository.BlacklistRepository;
import com.business.userservice.domain.user.repository.UserRepository;
import com.business.userservice.infrastructure.jwt.JwtUtil;
import com.github.themepark.common.application.exception.CustomException;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImplApiV1 implements UserServiceApiV1 {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BlacklistRepository blacklistRepository;
    private final JwtUtil jwtUtil;

    @Override
    public ResUserGetByIdDTOApiV1 getBy(Long id) {
        UserEntity userEntity = findById(id);
        return ResUserGetByIdDTOApiV1.of(userEntity);
    }

    @Override
    public ResUserGetDTOApiV1 getBy(Predicate predicate, Pageable pageable) {
        Page<UserEntity> userEntityPage = userRepository.findAll(predicate, pageable);
        return ResUserGetDTOApiV1.of(userEntityPage);
    }

    @Transactional
    @Override
    public ResAuthPutDTOApiV1 putBy(Long id, ReqUserPutDTOApiV1 dto) {
        UserEntity userEntity = findById(id);
        UserEntity updateUser = dto.getUser().update(userEntity);
        blacklistRepository.save(String.valueOf(userEntity.getId()));
        String accessToken = jwtUtil.createAccessToken(id, updateUser.getRole(), updateUser.getSlackId());
        String refreshToken = jwtUtil.createRefreshToken(accessToken);
        return ResAuthPutDTOApiV1.of(accessToken, refreshToken);
    }

    @Transactional
    @Override
    public void deleteById(Long id, ReqUserPostDeleteDTOApiV1 dto) {
        UserEntity userEntity = findById(id);
        validatePassword(dto.getUser().getPassword(), userEntity);
        userEntity.deletedBy(id);
    }

    @Transactional
    @Override
    public boolean blacklistById(Long id) {
        UserEntity userEntity = findById(id);
        return userEntity.blacklist();
    }

    private UserEntity findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new CustomException(UserExceptionCode.USER_NOT_FOUND));
    }

    private void validatePassword(String password, UserEntity userEntity) {
        if (!userEntity.isPasswordMatch(password, passwordEncoder)) {
            throw new CustomException(UserExceptionCode.PASSWORD_NOT_MATCH);
        }
    }
}
