package com.business.themeparkservice.hashtag.application.service;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPutDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagGetByIdDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagGetDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPutDTOApiV1;
import com.business.themeparkservice.hashtag.application.exception.HashtagExceptionCode;
import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import com.business.themeparkservice.hashtag.infastructure.persistence.hashtag.HashtagJpaRepository;
import com.github.themepark.common.application.exception.CustomException;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HashtagServiceImplApiV1 implements HashtagServiceApiV1{

    private final HashtagJpaRepository hashtagRepository;

    @Override
    public ResHashtagPostDTOApiV1 postBy(ReqHashtagPostDTOApiV1 reqDto) {
        HashtagEntity hashtagEntity = hashtagRepository.save(reqDto.createHashtag());
        return ResHashtagPostDTOApiV1.of(hashtagEntity);
    }


    @Transactional(readOnly = true)
    @Override
    public ResHashtagGetByIdDTOApiV1 getBy(UUID id) {
        HashtagEntity hashtagEntity = getHashtag(id);
        return ResHashtagGetByIdDTOApiV1.of(hashtagEntity);
    }

    @Override
    public ResHashtagGetDTOApiV1 getBy(Predicate predicate, Pageable pageable) {
        Page<HashtagEntity> hashtagEntityPage = hashtagRepository.findAll(predicate,pageable);
        return ResHashtagGetDTOApiV1.of(hashtagEntityPage);
    }

    @Override
    public ResHashtagPutDTOApiV1 putBy(UUID id, ReqHashtagPutDTOApiV1 reqDto) {
        HashtagEntity hashtagEntity = getHashtag(id);
        hashtagEntity.update(reqDto.getHashtag().getName());
        return ResHashtagPutDTOApiV1.of(hashtagEntity);
    }

    @Override
    public void deleteBy(UUID id) {
        getHashtag(id).deletedBy(1L);
    }

    private HashtagEntity getHashtag(UUID id) {
        return hashtagRepository.findById(id).orElseThrow(() -> new CustomException(HashtagExceptionCode.HASHTAG_NOT_FOUND));
    }
}
