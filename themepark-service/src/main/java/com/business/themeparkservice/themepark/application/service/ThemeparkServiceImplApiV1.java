package com.business.themeparkservice.themepark.application.service;

import com.business.themeparkservice.hashtag.application.exception.HashtagExceptionCode;
import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import com.business.themeparkservice.hashtag.infastructure.persistence.hashtag.HashtagJpaRepository;
import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPutDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkGetByIdDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkGetDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPostDTOApiv1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPutDTOApiV1;
import com.business.themeparkservice.themepark.application.exception.ThemeparkExceptionCode;
import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
import com.business.themeparkservice.themepark.domain.entity.ThemeparkHashtagEntity;
import com.business.themeparkservice.themepark.infastructure.persistence.themepark.ThemeparkHashtagJpaRepository;
import com.business.themeparkservice.themepark.infastructure.persistence.themepark.ThemeparkJpaRepository;
import com.github.themepark.common.application.exception.CustomException;
import com.querydsl.core.types.Predicate;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ThemeparkServiceImplApiV1 implements ThemeparkServiceApiV1 {

    private final ThemeparkJpaRepository themeparkRepository;

    private final ThemeparkHashtagJpaRepository themeparkHashtagRepository;

    private final HashtagJpaRepository hashtagRepository;

    @Override
    public ResThemeparkPostDTOApiv1 postBy(ReqThemeparkPostDTOApiV1 reqDto) {
        ThemeparkEntity themeparkEntity = themeparkRepository.save(reqDto.createThemepark());
        List<String> hashtagNames = addHashtagNames(themeparkEntity, reqDto);

        return ResThemeparkPostDTOApiv1.of(themeparkEntity,hashtagNames);
    }

    @Transactional(readOnly = true)
    @Override
    public ResThemeparkGetByIdDTOApiV1 getBy(UUID id) {
        ThemeparkEntity themeparkEntity = findThemepark(id);
        List<String> hashtagNames = findThmeparkHashtags(id);

        return ResThemeparkGetByIdDTOApiV1.of(themeparkEntity,hashtagNames);
    }

    @Transactional(readOnly = true)
    @Override
    public ResThemeparkGetDTOApiV1 getBy(Predicate predicate, Pageable pageable) {
        Map<UUID, List<String>> hashtagMap = new HashMap<>();
        Page<ThemeparkEntity> themeparkEntityPage= themeparkRepository.findAll(predicate,pageable);

        for(ThemeparkEntity themeparkEntity : themeparkEntityPage){
            List<String> hashtagNames = findThmeparkHashtags(themeparkEntity.getId());
            hashtagMap.put(themeparkEntity.getId(), hashtagNames);
        }

        return ResThemeparkGetDTOApiV1.of(themeparkEntityPage,hashtagMap);
    }

    @Override
    public ResThemeparkPutDTOApiV1 putBy(UUID id, ReqThemeparkPutDTOApiV1 reqDto) {
        ThemeparkEntity themeparkEntity = findThemepark(id);
        themeparkEntity.update(reqDto);

        List<String> hashtagNames = findThmeparkHashtags(id);

        if(reqDto.getThemepark().getHashtagList() != null){
            themeparkEntity.getThemeparkHashtagEntityList().clear();
            hashtagNames = addHashtagNames(themeparkEntity ,reqDto);
        }

        return ResThemeparkPutDTOApiV1.of(themeparkEntity, hashtagNames);
    }

    @Override
    public void deleteBy(UUID id,Long userId) {
        ThemeparkEntity themeparkEntity = findThemepark(id);
        themeparkEntity.deletedBy(userId);
        themeparkEntity.getThemeparkHashtagEntityList().clear();
    }

    private List<String> addHashtagNames(ThemeparkEntity themeparkEntity, ReqThemeparkPutDTOApiV1 reqDto) {
        List<ThemeparkHashtagEntity> themeparkHashtagEntityList = new ArrayList<>();

        for (ReqThemeparkPutDTOApiV1.ThemePark.Hashtag tagDto : reqDto.getThemepark().getHashtagList()) {
            HashtagEntity hashtagEntity = findHashtag(tagDto.getHashtagId());

            ThemeparkHashtagEntity themeparkHashtagEntity =
                    ThemeparkHashtagEntity.builder()
                            .themepark(themeparkEntity)
                            .hashtag(hashtagEntity)
                            .build();

            themeparkHashtagEntityList.add(themeparkHashtagEntity);

        }

        themeparkHashtagRepository.saveAll(themeparkHashtagEntityList);

        return themeparkHashtagEntityList.stream()
                .map(hashname -> hashname.getHashtag().getName())
                .toList();
    }


    private List<String> addHashtagNames(ThemeparkEntity themeparkEntity, ReqThemeparkPostDTOApiV1 reqDto) {
        List<ThemeparkHashtagEntity> themeparkHashtagEntityList = new ArrayList<>();

        for (ReqThemeparkPostDTOApiV1.ThemePark.Hashtag tagDto : reqDto.getThemepark().getHashtagList()) {
            HashtagEntity hashtagEntity = findHashtag(tagDto.getHashtagId());

            ThemeparkHashtagEntity themeparkHashtagEntity =
                    ThemeparkHashtagEntity.builder()
                            .themepark(themeparkEntity)
                            .hashtag(hashtagEntity)
                            .build();

            themeparkHashtagEntityList.add(themeparkHashtagEntity);

        }

        themeparkHashtagRepository.saveAll(themeparkHashtagEntityList);

        return themeparkHashtagEntityList.stream()
                .map(hashname -> hashname.getHashtag().getName())
                .toList();
    }

    private List<String> findThmeparkHashtags(UUID id) {
        List<HashtagEntity> hashtagEntityList = new ArrayList<>();

        List<ThemeparkHashtagEntity> themeparkHashtagEntityList =
                themeparkHashtagRepository.findAllByThemeparkId(id);


        for (ThemeparkHashtagEntity themeparkHashtagEntity : themeparkHashtagEntityList) {
            HashtagEntity hashtag = findHashtag(themeparkHashtagEntity.getHashtag().getId());

            hashtagEntityList.add(hashtag);
        }

        return hashtagEntityList.stream()
                .map(HashtagEntity::getName)
                .toList();
    }

    private HashtagEntity findHashtag(UUID id) {
        return hashtagRepository.findById(id)
                .orElseThrow(()->new CustomException(HashtagExceptionCode.HASHTAG_NOT_FOUND));
    }


    private ThemeparkEntity findThemepark(UUID id) {
        return themeparkRepository.findById(id)
                .orElseThrow(()->new CustomException(ThemeparkExceptionCode.THEMEPARK_NOT_FOUND));
    }
}
