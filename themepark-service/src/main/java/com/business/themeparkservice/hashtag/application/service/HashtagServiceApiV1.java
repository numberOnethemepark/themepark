package com.business.themeparkservice.hashtag.application.service;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPostDTOApiV1;
import jakarta.validation.Valid;

public interface HashtagServiceApiV1 {
    ResHashtagPostDTOApiV1 postBy(@Valid ReqHashtagPostDTOApiV1 reqDto);
}
