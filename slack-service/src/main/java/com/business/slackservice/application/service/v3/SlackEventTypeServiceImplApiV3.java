package com.business.slackservice.application.service.v3;

import com.business.slackservice.application.dto.v3.request.slackEventType.ReqSlackEventTypePostDTOApiV3;
import com.business.slackservice.application.dto.v3.request.slackEventType.ReqSlackEventTypePutDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackEventType.ResSlackEventTypeGetByIdDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackEventType.ResSlackEventTypeGetDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackEventType.ResSlackEventTypePostDTOApiV3;
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
public class SlackEventTypeServiceImplApiV3 implements SlackEventTypeServiceApiV3 {

    private final SlackEventTypeRepository slackEventTypeRepository;
    private final SlackEventTypeService slackEventTypeService;

    @Transactional
    @Override
    public ResSlackEventTypePostDTOApiV3 postBy(ReqSlackEventTypePostDTOApiV3 dto) {
        String name = dto.getSlackEventType().getName();
        if (slackEventTypeRepository.existsByName(name)) {
            throw new CustomException(SlackExceptionCode.SLACK_EVENT_TYPE_NAME_DUPLICATED);
        }
        SlackEventTypeEntity savedSlackEventType = slackEventTypeRepository.save(dto.getSlackEventType().toEntity());
        return ResSlackEventTypePostDTOApiV3.of(savedSlackEventType);
    }

    @Override
    public ResSlackEventTypeGetByIdDTOApiV3 getBy(UUID id) {
        SlackEventTypeEntity slackEventTypeEntity = slackEventTypeService.getBy(id);
        return ResSlackEventTypeGetByIdDTOApiV3.of(slackEventTypeEntity);
    }

    @Override
    public ResSlackEventTypeGetDTOApiV3 getBy(Predicate predicate, Pageable pageable) {
        Page<SlackEventTypeEntity> slackEventTypeEntityPage = slackEventTypeRepository.findAll(predicate, pageable);
        return ResSlackEventTypeGetDTOApiV3.of(slackEventTypeEntityPage);
    }

    @Transactional
    @Override
    public void putBy(UUID id, ReqSlackEventTypePutDTOApiV3 dto) {
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
