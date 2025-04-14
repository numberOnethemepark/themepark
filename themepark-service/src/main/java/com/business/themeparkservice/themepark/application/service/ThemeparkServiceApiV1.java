package com.business.themeparkservice.themepark.application.service;

import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkGetByIdDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPostDTOApiv1;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface ThemeparkServiceApiV1 {
    ResThemeparkPostDTOApiv1 postBy(@Valid ReqThemeparkPostDTOApiV1 reqDto);

    ResThemeparkGetByIdDTOApiV1 getBy(UUID id);
}
