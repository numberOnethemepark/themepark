package com.business.userservice.domain.user.repository;

import com.business.userservice.domain.user.entity.UserEntity;
import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findBySlackId(String slackId);

    UserEntity save(UserEntity saveUser);
}
