package com.business.themeparkservice.themepark.domain.repository;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkHashtagEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ThemeparkHashtagRepository {
    List<ThemeparkHashtagEntity> findAllByThemeparkId(UUID id);
}
