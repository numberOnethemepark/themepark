package com.business.themeparkservice.waiting.application.service;

import com.business.themeparkservice.waiting.application.dto.request.ReqWaitingPostDTOApiV1;
import com.business.themeparkservice.waiting.application.dto.response.ResWaitingPostDTOApiV1;
import jakarta.validation.Valid;

public interface WaitingServiceApiV1 {
    ResWaitingPostDTOApiV1 postBy(@Valid ReqWaitingPostDTOApiV1 reqDto);
}
