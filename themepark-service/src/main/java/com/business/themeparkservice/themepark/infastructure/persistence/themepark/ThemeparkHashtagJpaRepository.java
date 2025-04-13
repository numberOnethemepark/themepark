package com.business.themeparkservice.themepark.infastructure.persistence.themepark;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkHashtagEntity;
import com.business.themeparkservice.themepark.domain.repository.ThemeparkHashtagRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ThemeparkHashtagJpaRepository extends JpaRepository<ThemeparkHashtagEntity, UUID>, ThemeparkHashtagRepository {
}
