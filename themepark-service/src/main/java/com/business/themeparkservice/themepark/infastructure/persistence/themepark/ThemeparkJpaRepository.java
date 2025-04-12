package com.business.themeparkservice.themepark.infastructure.persistence.themepark;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
import com.business.themeparkservice.themepark.domain.repository.ThemeparkRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ThemeparkJpaRepository extends JpaRepository<ThemeparkEntity, UUID>, ThemeparkRepository {
}
