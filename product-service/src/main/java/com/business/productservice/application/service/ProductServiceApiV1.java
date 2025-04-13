package com.business.productservice.application.service;

import com.business.productservice.application.dto.request.ReqProductPostDTOApiV1;
import com.business.productservice.application.dto.response.ResProductPostDTOApiV1;
import jakarta.validation.Valid;

public interface ProductServiceApiV1 {
    ResProductPostDTOApiV1 postBy(@Valid ReqProductPostDTOApiV1 reqDto);
}
