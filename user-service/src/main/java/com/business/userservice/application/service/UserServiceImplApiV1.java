package com.business.userservice.application.service;

import com.business.userservice.application.dto.response.ResUserGetByIdDTOApiV1;
import com.business.userservice.domain.user.entity.UserEntity;
import com.business.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImplApiV1 implements UserServiceApiV1 {

    private final UserRepository userRepository;

    @Override
    public ResUserGetByIdDTOApiV1 getBy(Long id) {
        UserEntity userEntity = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 아이디 입니다."));

        return ResUserGetByIdDTOApiV1.of(userEntity);
    }
}
