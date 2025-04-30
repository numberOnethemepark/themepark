package com.business.productservice.application.service.v3;

import com.business.productservice.application.dto.v3.request.ReqProductPostDTOApiV3;
import com.business.productservice.application.dto.v3.request.ReqProductPutDTOApiV3;
import com.business.productservice.application.dto.v3.response.*;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductServiceApiV3 {
    ResProductPostDTOApiV3 postBy(@Valid ReqProductPostDTOApiV3 reqDto);

    ResProductGetByIdDTOApiV3 getBy(UUID id);

    ResProductGetDTOApiV3 getBy(Predicate predicate, Pageable pageable);

    ResProductPutDTOApiV3 putBy(UUID id, ReqProductPutDTOApiV3 dto);

    void deleteBy(UUID id);

    void postDecreaseById(UUID id, String orderId);

    void postRestoreById(UUID id);

    ResStockGetByIdDTOApiV3 getStockById(UUID id);
}
