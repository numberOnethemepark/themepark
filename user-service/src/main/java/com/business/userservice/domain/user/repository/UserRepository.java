package com.business.userservice.domain.user.repository;

import com.business.userservice.domain.user.entity.UserEntity;
import com.querydsl.core.types.Predicate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findBySlackId(String slackId);

    UserEntity save(UserEntity saveUser);

    Optional<UserEntity> findById(Long id);

    Page<UserEntity> findAll(Predicate predicate, Pageable pageable);
}
