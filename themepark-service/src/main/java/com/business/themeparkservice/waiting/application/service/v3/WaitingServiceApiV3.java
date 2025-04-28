package com.business.themeparkservice.waiting.application.service.v3;

import com.business.themeparkservice.waiting.application.dto.request.v3.ReqWaitingPostDTOApiV3;
import com.business.themeparkservice.waiting.application.dto.response.v3.*;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface WaitingServiceApiV3 {
    ResWaitingPostDTOApiV3 postBy(@Valid ReqWaitingPostDTOApiV3 reqDto, Long userId);

    ResWaitingGetByIdDTOApiV3 getBy(UUID id);

    ResWaitingGetDTOApiV3 getBy(Predicate predicate, Pageable pageable);

    ResWaitingPostDoneDTOApiV3 postDoneBy(UUID id);

    ResWaitingPostCancelDTOApiV3 postCancelBy(UUID id);

    void deleteBy(UUID id, Long userId);
}
