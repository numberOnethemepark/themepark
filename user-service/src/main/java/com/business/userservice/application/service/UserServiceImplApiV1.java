package com.business.userservice.application.service;

import com.business.userservice.application.dto.request.ReqUserPutDTOApiV1;
import com.business.userservice.application.dto.response.ResUserGetByIdDTOApiV1;
import com.business.userservice.application.dto.response.ResUserGetDTOApiV1;
import com.business.userservice.domain.user.entity.UserEntity;
import com.business.userservice.domain.user.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImplApiV1 implements UserServiceApiV1 {

    private final UserRepository userRepository;

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
    public void putBy(Long id, ReqUserPutDTOApiV1 dto) {
        UserEntity userEntity = findById(id);
        dto.getUser().update(userEntity);
    }

    private UserEntity findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 아이디 입니다."));
    }
}
