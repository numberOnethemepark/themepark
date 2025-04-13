package com.business.themeparkservice.hashtag.application.service;

import com.business.themeparkservice.hashtag.application.dto.request.ReqHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.application.dto.response.ResHashtagPostDTOApiV1;
import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import com.business.themeparkservice.hashtag.domain.repository.HashtagRepository;
import com.business.themeparkservice.hashtag.infastructure.persistence.hashtag.HashtagJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HashtagServiceImplApiV1 implements HashtagServiceApiV1{

    private final HashtagJpaRepository hashtagRepository;
    @Override
    public ResHashtagPostDTOApiV1 postBy(ReqHashtagPostDTOApiV1 reqDto) {
        HashtagEntity hashtagEntity = hashtagRepository.save(reqDto.createHashtag());
        hashtagRepository.flush();
        System.out.println("hashtagEntity = " + hashtagEntity);
        return ResHashtagPostDTOApiV1.of(hashtagEntity);
    }
}
