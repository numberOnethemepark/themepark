package com.business.themeparkservice.hashtag.application.service;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPostDTOApiV1;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPutDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagGetByIdDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagGetDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPutDTOApiV1;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HashtagServiceApiV1 {
    ResHashtagPostDTOApiV1 postBy(@Valid ReqHashtagPostDTOApiV1 reqDto);

    ResHashtagGetByIdDTOApiV1 getBy(UUID id);

    ResHashtagGetDTOApiV1 getBy(Predicate predicate, Pageable pageable);

    ResHashtagPutDTOApiV1 putBy(UUID id, ReqHashtagPutDTOApiV1 reqDto);
}
