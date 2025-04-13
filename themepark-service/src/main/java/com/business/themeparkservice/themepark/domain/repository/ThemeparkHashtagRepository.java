package com.business.themeparkservice.themepark.domain.repository;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkHashtagEntity;

import java.util.List;

public interface ThemeparkHashtagRepository {
    void saveAll(List<ThemeparkHashtagEntity> themeparkHashtagEntityList);
}
