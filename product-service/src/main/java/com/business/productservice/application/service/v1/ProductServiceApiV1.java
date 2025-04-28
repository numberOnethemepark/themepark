package com.business.productservice.application.service.v1;

import com.business.productservice.application.dto.v1.request.ReqProductPostDTOApiV1;
import com.business.productservice.application.dto.v1.request.ReqProductPutDTOApiV1;
import com.business.productservice.application.dto.v1.response.*;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductServiceApiV1 {
    ResProductPostDTOApiV1 postBy(@Valid ReqProductPostDTOApiV1 reqDto);

    ResProductGetByIdDTOApiV1 getBy(UUID id);

    ResProductGetDTOApiV1 getBy(Predicate predicate, Pageable pageable);

    ResProductPutDTOApiV1 putBy(UUID id, ReqProductPutDTOApiV1 dto);

    void deleteBy(UUID id);

    void postDecreaseById(UUID id);

    void postRestoreById(UUID id);

    ResStockGetByIdDTOApiV1 getStockById(UUID id);
}
