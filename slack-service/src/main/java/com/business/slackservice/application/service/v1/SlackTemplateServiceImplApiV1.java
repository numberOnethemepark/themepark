package com.business.slackservice.application.service.v1;

import com.business.slackservice.application.dto.v1.request.slackTemplate.ReqSlackTemplatePostDTOApiV1;
import com.business.slackservice.application.dto.v1.request.slackTemplate.ReqSlackTemplatePutDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slackTemplate.ResSlackTemplateGetByIdDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slackTemplate.ResSlackTemplateGetDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slackTemplate.ResSlackTemplatePostDTOApiV1;
import com.business.slackservice.application.exception.SlackExceptionCode;
import com.business.slackservice.application.service.SlackEventTypeService;
import com.business.slackservice.application.service.SlackTemplateService;
import com.business.slackservice.domain.slackEventType.entity.SlackEventTypeEntity;
import com.business.slackservice.domain.slackTemplate.entity.SlackTemplateEntity;
import com.business.slackservice.domain.slackTemplate.repository.SlackTemplateRepository;
import com.github.themepark.common.application.exception.CustomException;
import com.querydsl.core.types.Predicate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SlackTemplateServiceImplApiV1 implements SlackTemplateServiceApiV1 {

    private final SlackTemplateRepository slackTemplateRepository;
    private final SlackEventTypeService slackEventTypeService;
    private final SlackTemplateService slackTemplateService;

    @Transactional
    @Override
    public ResSlackTemplatePostDTOApiV1 postBy(ReqSlackTemplatePostDTOApiV1 dto) {
        SlackEventTypeEntity slackEventTypeEntity = slackEventTypeService.getBy(
            dto.getSlackTemplate().getSlackEventTypeId()
        );
        if (slackTemplateRepository.existsBySlackEventTypeEntity(slackEventTypeEntity)) {
            throw new CustomException(SlackExceptionCode.SLACK_TEMPLATE_DUPLICATED);
        }
        SlackTemplateEntity slackTemplateEntity = dto.getSlackTemplate()
            .toEntityBy(slackEventTypeEntity);
        return ResSlackTemplatePostDTOApiV1.of(slackTemplateRepository.save(slackTemplateEntity));
    }

    @Override
    public ResSlackTemplateGetByIdDTOApiV1 getBy(UUID id) {
        return ResSlackTemplateGetByIdDTOApiV1.of(slackTemplateService.getBy(id));
    }

    @Override
    public ResSlackTemplateGetDTOApiV1 getBy(Predicate predicate, Pageable pageable) {
        Page<SlackTemplateEntity> slackTemplateEntityPage = slackTemplateRepository.findAll(
            predicate, pageable);
        return ResSlackTemplateGetDTOApiV1.of(slackTemplateEntityPage);
    }

    @Transactional
    @Override
    public void putBy(UUID id, ReqSlackTemplatePutDTOApiV1 dto) {
        SlackTemplateEntity slackTemplateEntity = slackTemplateService.getBy(id);
        UUID slackEventTypeId = dto.getSlackTemplate().getSlackEventTypeId();
        SlackEventTypeEntity slackEventTypeEntity =
            (slackEventTypeId != null) ? slackEventTypeService.getBy(slackEventTypeId) : null;
        dto.getSlackTemplate().update(slackTemplateEntity, slackEventTypeEntity);
    }

    @Transactional
    @Override
    public void deleteBy(UUID id, Long userId) {
        SlackTemplateEntity slackTemplateEntity = slackTemplateService.getBy(id);
        slackTemplateEntity.deletedBy(userId);
    }
}
