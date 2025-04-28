package com.business.slackservice.application.service.v2;

import com.business.slackservice.application.dto.v2.request.slackTemplate.ReqSlackTemplatePostDTOApiV2;
import com.business.slackservice.application.dto.v2.request.slackTemplate.ReqSlackTemplatePutDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackTemplate.ResSlackTemplateGetByIdDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackTemplate.ResSlackTemplateGetDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackTemplate.ResSlackTemplatePostDTOApiV2;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface SlackTemplateServiceApiV2 {

    ResSlackTemplatePostDTOApiV2 postBy(@Valid ReqSlackTemplatePostDTOApiV2 dto);

    ResSlackTemplateGetByIdDTOApiV2 getBy(UUID id);

    ResSlackTemplateGetDTOApiV2 getBy(Predicate predicate, Pageable pageable);

    void putBy(UUID id, @Valid ReqSlackTemplatePutDTOApiV2 dto);

    void deleteBy(UUID id, Long userId);
}
