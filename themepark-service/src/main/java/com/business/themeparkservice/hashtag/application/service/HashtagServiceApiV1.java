package com.business.themeparkservice.hashtag.application.service;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagGetByIdDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPostDTOApiV1;
import jakarta.validation.Valid;

import java.util.UUID;

public interface HashtagServiceApiV1 {
    ResHashtagPostDTOApiV1 postBy(@Valid ReqHashtagPostDTOApiV1 reqDto);

    ResHashtagGetByIdDTOApiV1 getBy(UUID id);
}
