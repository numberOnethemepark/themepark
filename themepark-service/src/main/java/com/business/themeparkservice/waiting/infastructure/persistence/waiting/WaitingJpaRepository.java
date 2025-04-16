package com.business.themeparkservice.waiting.infastructure.persistence.waiting;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
import com.business.themeparkservice.waiting.domain.repository.WaitingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WaitingJpaRepository extends
        JpaRepository<WaitingEntity, UUID>, WaitingRepository, QuerydslPredicateExecutor<WaitingEntity> {
}
