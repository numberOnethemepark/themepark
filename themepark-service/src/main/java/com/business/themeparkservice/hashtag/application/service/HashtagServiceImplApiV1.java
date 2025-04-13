package com.business.themeparkservice.hashtag.application.service;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagGetByIdDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import com.business.themeparkservice.hashtag.infastructure.persistence.hashtag.HashtagJpaRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

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

    @Override
    public ResHashtagGetByIdDTOApiV1 getBy(UUID id) {
        HashtagEntity hashtagEntity = getHashtag(id);
        return ResHashtagGetByIdDTOApiV1.of(hashtagEntity);
    }

    private HashtagEntity getHashtag(UUID id) {
        return hashtagRepository.findById(id).orElseThrow(()
                -> new NotFoundException("해시태그가 존재하지않습니다."));
    }
}
