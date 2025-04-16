package com.business.themeparkservice.waiting.application.service;

import com.business.themeparkservice.waiting.application.dto.request.ReqWaitingPostDTOApiV1;
import com.business.themeparkservice.waiting.application.dto.response.ResWaitingGetByIdDTOApiV1;
import com.business.themeparkservice.waiting.application.dto.response.ResWaitingPostDTOApiV1;
import com.business.themeparkservice.waiting.application.dto.response.ResWaitingPostDoneDTOApiV1;
import jakarta.validation.Valid;

import java.util.UUID;

public interface WaitingServiceApiV1 {
    ResWaitingPostDTOApiV1 postBy(@Valid ReqWaitingPostDTOApiV1 reqDto);

    ResWaitingGetByIdDTOApiV1 getBy(UUID id);

    ResWaitingPostDoneDTOApiV1 postDoneBy(UUID id);
}
