package com.business.productservice.application.service.v2;

import com.business.productservice.application.dto.v1.response.*;
import com.business.productservice.application.dto.v2.request.ReqProductPostDTOApiV2;
import com.business.productservice.application.dto.v2.request.ReqProductPutDTOApiV2;
import com.business.productservice.application.dto.v2.response.*;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductServiceApiV2 {
    ResProductPostDTOApiV2 postBy(@Valid ReqProductPostDTOApiV2 reqDto);

    ResProductGetByIdDTOApiV2 getBy(UUID id);

    ResProductGetDTOApiV2 getBy(Predicate predicate, Pageable pageable);

    ResProductPutDTOApiV2 putBy(UUID id, ReqProductPutDTOApiV2 dto);

    void deleteBy(UUID id);

    void postDecreaseById(UUID id);

    void postRestoreById(UUID id);

    ResStockGetByIdDTOApiV2 getStockById(UUID id);
}
