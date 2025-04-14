package com.business.themeparkservice.themepark.domain.repository;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThemeparkRepository {
    Page<ThemeparkEntity> findAll(Predicate predicate, Pageable pageable);
}
