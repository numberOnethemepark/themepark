package com.business.themeparkservice.themepark.application.service;

import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import com.business.themeparkservice.hashtag.infastructure.persistence.hashtag.HashtagJpaRepository;
import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkGetByIdDTOApiV1;
import com.business.themeparkservice.themepark.application.dto.response.ResThemeparkPostDTOApiv1;
import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
import com.business.themeparkservice.themepark.domain.entity.ThemeparkHashtagEntity;
import com.business.themeparkservice.themepark.infastructure.persistence.themepark.ThemeparkHashtagJpaRepository;
import com.business.themeparkservice.themepark.infastructure.persistence.themepark.ThemeparkJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public ResThemeparkGetByIdDTOApiV1 getBy(UUID id) {
        ThemeparkEntity themeparkEntity = findThemepark(id);
        List<String> hashtagNames = findThmeparkHashtags(id);

        return ResThemeparkGetByIdDTOApiV1.of(themeparkEntity,hashtagNames);
    }



    private List<String> addHashtagNames(ThemeparkEntity themeparkEntity, ReqThemeparkPostDTOApiV1 reqDto) {
        List<ThemeparkHashtagEntity> themeparkHashtagEntityList = new ArrayList<>();

        for (ReqThemeparkPostDTOApiV1.ThemePark.Hashtag tagDto : reqDto.getThemepark().getHashtagList()) {

            HashtagEntity hashtagEntity = hashtagRepository.findById(tagDto.getHashtagId())
                    .orElseThrow(()->new EntityNotFoundException("Hashtag not found"));

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


        for (ThemeparkHashtagEntity hashtagEntity : themeparkHashtagEntityList) {
            HashtagEntity hashtag = hashtagRepository.findById(hashtagEntity.getHashtag().getId())
                    .orElseThrow(()->new EntityNotFoundException("Hashtag not found"));

            hashtagEntityList.add(hashtag);
        }

        return hashtagEntityList.stream()
                .map(HashtagEntity::getName)
                .toList();
    }


    private ThemeparkEntity findThemepark(UUID id) {
        return themeparkRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Hashtag not found"));
    }
}
