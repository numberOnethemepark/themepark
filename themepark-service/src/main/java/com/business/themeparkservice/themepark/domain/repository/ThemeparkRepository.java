package com.business.themeparkservice.themepark.domain.repository;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;

public interface ThemeparkRepository {
    ThemeparkEntity save(ThemeparkEntity themepark);
}
