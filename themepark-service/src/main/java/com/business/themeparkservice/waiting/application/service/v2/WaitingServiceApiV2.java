package com.business.themeparkservice.waiting.application.service.v2;

import com.business.themeparkservice.waiting.application.dto.request.v2.ReqWaitingPostDTOApiV2;
import com.business.themeparkservice.waiting.application.dto.response.v2.*;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface WaitingServiceApiV2 {
    ResWaitingPostDTOApiV2 postBy(@Valid ReqWaitingPostDTOApiV2 reqDto, Long userId);

    ResWaitingGetByIdDTOApiV2 getBy(UUID id);

    ResWaitingGetDTOApiV2 getBy(Predicate predicate, Pageable pageable);

    ResWaitingPostDoneDTOApiV2 postDoneBy(UUID id);

    ResWaitingPostCancelDTOApiV2 postCancelBy(UUID id);

    void deleteBy(UUID id, Long userId);
}
