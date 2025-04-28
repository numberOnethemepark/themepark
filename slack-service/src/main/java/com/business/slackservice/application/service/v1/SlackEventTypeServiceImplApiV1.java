package com.business.slackservice.application.service.v1;

import com.business.slackservice.application.dto.v1.request.slackEventType.ReqSlackEventTypePostDTOApiV1;
import com.business.slackservice.application.dto.v1.request.slackEventType.ReqSlackEventTypePutDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slackEventType.ResSlackEventTypeGetByIdDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slackEventType.ResSlackEventTypeGetDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slackEventType.ResSlackEventTypePostDTOApiV1;
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
public class SlackEventTypeServiceImplApiV1 implements SlackEventTypeServiceApiV1 {

    private final SlackEventTypeRepository slackEventTypeRepository;
    private final SlackEventTypeService slackEventTypeService;

    @Transactional
    @Override
    public ResSlackEventTypePostDTOApiV1 postBy(ReqSlackEventTypePostDTOApiV1 dto) {
        String name = dto.getSlackEventType().getName();
        if (slackEventTypeRepository.existsByName(name)) {
            throw new CustomException(SlackExceptionCode.SLACK_EVENT_TYPE_NAME_DUPLICATED);
        }
        SlackEventTypeEntity savedSlackEventType = slackEventTypeRepository.save(dto.getSlackEventType().toEntity());
        return ResSlackEventTypePostDTOApiV1.of(savedSlackEventType);
    }

    @Override
    public ResSlackEventTypeGetByIdDTOApiV1 getBy(UUID id) {
        SlackEventTypeEntity slackEventTypeEntity = slackEventTypeService.getBy(id);
        return ResSlackEventTypeGetByIdDTOApiV1.of(slackEventTypeEntity);
    }

    @Override
    public ResSlackEventTypeGetDTOApiV1 getBy(Predicate predicate, Pageable pageable) {
        Page<SlackEventTypeEntity> slackEventTypeEntityPage = slackEventTypeRepository.findAll(predicate, pageable);
        return ResSlackEventTypeGetDTOApiV1.of(slackEventTypeEntityPage);
    }

    @Transactional
    @Override
    public void putBy(UUID id, ReqSlackEventTypePutDTOApiV1 dto) {
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
