package com.business.slackservice.application.service.v2;

import com.business.slackservice.application.dto.v2.request.slackEventType.ReqSlackEventTypePostDTOApiV2;
import com.business.slackservice.application.dto.v2.request.slackEventType.ReqSlackEventTypePutDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackEventType.ResSlackEventTypeGetByIdDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackEventType.ResSlackEventTypeGetDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackEventType.ResSlackEventTypePostDTOApiV2;
import com.business.slackservice.application.exception.SlackExceptionCode;
import com.business.slackservice.application.service.SlackEventTypeService;
import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackEventType.repository.SlackEventTypeRepository;
import com.github.themepark.common.application.exception.CustomException;
import com.querydsl.core.types.Predicate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SlackEventTypeServiceImplApiV2 implements SlackEventTypeServiceApiV2 {

    private final SlackEventTypeRepository slackEventTypeRepository;
    private final SlackEventTypeService slackEventTypeService;

    @Transactional
    @Override
    public ResSlackEventTypePostDTOApiV2 postBy(ReqSlackEventTypePostDTOApiV2 dto) {
        String name = dto.getSlackEventType().getName();
        if (slackEventTypeRepository.existsByName(name)) {
            throw new CustomException(SlackExceptionCode.SLACK_EVENT_TYPE_NAME_DUPLICATED);
        }
        SlackEventTypeEntity savedSlackEventType = slackEventTypeRepository.save(dto.getSlackEventType().toEntity());
        return ResSlackEventTypePostDTOApiV2.of(savedSlackEventType);
    }

    @Override
    public ResSlackEventTypeGetByIdDTOApiV2 getBy(UUID id) {
        SlackEventTypeEntity slackEventTypeEntity = slackEventTypeService.getBy(id);
        return ResSlackEventTypeGetByIdDTOApiV2.of(slackEventTypeEntity);
    }

    @Override
    public ResSlackEventTypeGetDTOApiV2 getBy(Predicate predicate, Pageable pageable) {
        Page<SlackEventTypeEntity> slackEventTypeEntityPage = slackEventTypeRepository.findAll(predicate, pageable);
        return ResSlackEventTypeGetDTOApiV2.of(slackEventTypeEntityPage);
    }

    @Transactional
    @Override
    public void putBy(UUID id, ReqSlackEventTypePutDTOApiV2 dto) {
        SlackEventTypeEntity slackEventTypeEntity = slackEventTypeService.getBy(id);
        dto.getSlackEventType().update(slackEventTypeEntity);
    }

    @Transactional
    @Override
    public void deleteBy(UUID id, Long userId) {
        SlackEventTypeEntity slackEventTypeEntity = slackEventTypeService.getBy(id);
        slackEventTypeEntity.deletedBy(userId);
    }
}
