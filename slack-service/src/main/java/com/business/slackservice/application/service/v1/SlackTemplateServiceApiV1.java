package com.business.slackservice.application.service.v1;

import com.business.slackservice.application.dto.v1.request.slackTemplate.ReqSlackTemplatePostDTOApiV1;
import com.business.slackservice.application.dto.v1.request.slackTemplate.ReqSlackTemplatePutDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slackTemplate.ResSlackTemplateGetByIdDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slackTemplate.ResSlackTemplateGetDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slackTemplate.ResSlackTemplatePostDTOApiV1;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface SlackTemplateServiceApiV1 {

    ResSlackTemplatePostDTOApiV1 postBy(@Valid ReqSlackTemplatePostDTOApiV1 dto);

    ResSlackTemplateGetByIdDTOApiV1 getBy(UUID id);

    ResSlackTemplateGetDTOApiV1 getBy(Predicate predicate, Pageable pageable);

    void putBy(UUID id, @Valid ReqSlackTemplatePutDTOApiV1 dto);

    void deleteBy(UUID id, Long userId);
}
