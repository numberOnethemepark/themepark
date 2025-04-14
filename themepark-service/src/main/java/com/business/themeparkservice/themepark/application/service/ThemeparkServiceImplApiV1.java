package com.business.themeparkservice.themepark.application.service;

import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import com.business.themeparkservice.hashtag.infastructure.persistence.hashtag.HashtagJpaRepository;
import com.business.themeparkservice.themepark.application.dto.request.ReqThemeparkPostDTOApiV1;
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
        List<ThemeparkHashtagEntity> themeparkHashtagEntityList = new ArrayList<>();
        ThemeparkEntity themeparkEntity = themeparkRepository.save(reqDto.createThemepark());

        //중간테이블 입력
        for (ReqThemeparkPostDTOApiV1.ThemePark.Hashtag tagDto : reqDto.getThemepark().getHashtagList()) {
            UUID tagId = tagDto.getHashtagId();

            HashtagEntity hashtagEntity = hashtagRepository.findById(tagId)
                    .orElseThrow(()->new EntityNotFoundException("Hashtag not found"));

            ThemeparkHashtagEntity themeparkHashtagEntity =
                    ThemeparkHashtagEntity.builder()
                            .themepark(themeparkEntity)
                            .hashtag(hashtagEntity)
                            .build();

            themeparkHashtagEntityList.add(themeparkHashtagEntity);

        }
        themeparkHashtagRepository.saveAll(themeparkHashtagEntityList);
        // 저장된 테마파크와 해시태그 이름을 응답에 포함시키기 위해 해시태그 이름 리스트 만들기
        List<String> hashtagNames = themeparkHashtagEntityList.stream()
                .map(hashname -> hashname.getHashtag().getName())  // 해시태그 이름만 추출
                .toList();


        //응답반환
        return ResThemeparkPostDTOApiv1.of(themeparkEntity,hashtagNames);
    }
}
