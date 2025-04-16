package com.business.userservice.infrastructure.persistence.user;

import com.business.userservice.domain.user.entity.UserEntity;
import com.business.userservice.domain.user.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserJpaRepository extends UserRepository, JpaRepository<UserEntity, Long>,
    QuerydslPredicateExecutor<UserEntity> {

}
