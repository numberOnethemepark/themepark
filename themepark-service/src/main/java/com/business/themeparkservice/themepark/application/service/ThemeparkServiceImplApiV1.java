package com.business.themeparkservice.themepark.application.service;

import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPostDTOApiv1;
import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
import com.business.themeparkservice.themepark.infastructure.persistence.themepark.ThemeparkJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ThemeparkServiceImplApiV1 implements ThemeparkServiceApiV1 {

    private final ThemeparkJpaRepository themeparkRepository;

    @Override
    public ResThemeparkPostDTOApiv1 postBy(ReqThemeparkPostDTOApiV1 reqDto) {
        ThemeparkEntity themeparkEntity = themeparkRepository.save(reqDto.createThemepark());
        return ResThemeparkPostDTOApiv1.of(themeparkEntity);
    }
}
