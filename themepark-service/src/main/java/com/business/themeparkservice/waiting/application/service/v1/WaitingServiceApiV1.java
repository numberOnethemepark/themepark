package com.business.themeparkservice.waiting.application.service.v1;

import com.business.themeparkservice.waiting.application.dto.request.v1.ReqWaitingPostDTOApiV1;
import com.business.themeparkservice.waiting.application.dto.response.v1.*;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface WaitingServiceApiV1 {
    ResWaitingPostDTOApiV1 postBy(@Valid ReqWaitingPostDTOApiV1 reqDto, Long userId);

    ResWaitingGetByIdDTOApiV1 getBy(UUID id);

    ResWaitingGetDTOApiV1 getBy(Predicate predicate, Pageable pageable);

    ResWaitingPostDoneDTOApiV1 postDoneBy(UUID id);

    ResWaitingPostCancelDTOApiV1 postCancelBy(UUID id);

    void deleteBy(UUID id, Long userId);
}