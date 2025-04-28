package com.business.slackservice.application.service.v2;

import com.business.slackservice.application.dto.v2.request.slackTemplate.ReqSlackTemplatePostDTOApiV2;
import com.business.slackservice.application.dto.v2.request.slackTemplate.ReqSlackTemplatePutDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackTemplate.ResSlackTemplateGetByIdDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackTemplate.ResSlackTemplateGetDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackTemplate.ResSlackTemplatePostDTOApiV2;
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
public class SlackTemplateServiceImplApiV2 implements SlackTemplateServiceApiV2 {

    private final SlackTemplateRepository slackTemplateRepository;
    private final SlackEventTypeService slackEventTypeService;
    private final SlackTemplateService slackTemplateService;

    @Transactional
    @Override
    public ResSlackTemplatePostDTOApiV2 postBy(ReqSlackTemplatePostDTOApiV2 dto) {
        SlackEventTypeEntity slackEventTypeEntity = slackEventTypeService.getBy(
            dto.getSlackTemplate().getSlackEventTypeId()
        );
        if (slackTemplateRepository.existsBySlackEventTypeEntity(slackEventTypeEntity)) {
            throw new CustomException(SlackExceptionCode.SLACK_TEMPLATE_DUPLICATED);
        }
        SlackTemplateEntity slackTemplateEntity = dto.getSlackTemplate()
            .toEntityBy(slackEventTypeEntity);
        return ResSlackTemplatePostDTOApiV2.of(slackTemplateRepository.save(slackTemplateEntity));
    }

    @Override
    public ResSlackTemplateGetByIdDTOApiV2 getBy(UUID id) {
        return ResSlackTemplateGetByIdDTOApiV2.of(slackTemplateService.getBy(id));
    }

    @Override
    public ResSlackTemplateGetDTOApiV2 getBy(Predicate predicate, Pageable pageable) {
        Page<SlackTemplateEntity> slackTemplateEntityPage = slackTemplateRepository.findAll(
            predicate, pageable);
        return ResSlackTemplateGetDTOApiV2.of(slackTemplateEntityPage);
    }

    @Transactional
    @Override
    public void putBy(UUID id, ReqSlackTemplatePutDTOApiV2 dto) {
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
