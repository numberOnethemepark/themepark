package com.business.themeparkservice.themepark.application.service;

import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPutDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkGetByIdDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkGetDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPostDTOApiv1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPutDTOApiV1;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface ThemeparkServiceApiV1 {
    ResThemeparkPostDTOApiv1 postBy(@Valid ReqThemeparkPostDTOApiV1 reqDto);

    ResThemeparkGetByIdDTOApiV1 getBy(UUID id);
  
    ResThemeparkGetDTOApiV1 getBy(Predicate predicate, Pageable pageable);

    ResThemeparkPutDTOApiV1 putBy(UUID id, ReqThemeparkPutDTOApiV1 reqDto);

    void deleteBy(UUID id);
}
