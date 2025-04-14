package com.business.productservice.application.service;

import com.business.productservice.application.dto.request.ReqProductPostDTOApiV1;
import com.business.productservice.application.dto.response.ResProductGetByIdDTOApiV1;
import com.business.productservice.application.dto.response.ResProductPostDTOApiV1;
import jakarta.validation.Valid;

import java.util.UUID;

public interface ProductServiceApiV1 {
    ResProductPostDTOApiV1 postBy(@Valid ReqProductPostDTOApiV1 reqDto);

    ResProductGetByIdDTOApiV1 getBy(UUID id);
}
