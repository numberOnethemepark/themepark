package com.business.slackservice.application.service.v3;

import com.business.slackservice.application.dto.v3.request.slackTemplate.ReqSlackTemplatePostDTOApiV3;
import com.business.slackservice.application.dto.v3.request.slackTemplate.ReqSlackTemplatePutDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackTemplate.ResSlackTemplateGetByIdDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackTemplate.ResSlackTemplateGetDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackTemplate.ResSlackTemplatePostDTOApiV3;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface SlackTemplateServiceApiV3 {

    ResSlackTemplatePostDTOApiV3 postBy(@Valid ReqSlackTemplatePostDTOApiV3 dto);

    ResSlackTemplateGetByIdDTOApiV3 getBy(UUID id);

    ResSlackTemplateGetDTOApiV3 getBy(Predicate predicate, Pageable pageable);

    void putBy(UUID id, @Valid ReqSlackTemplatePutDTOApiV3 dto);

    void deleteBy(UUID id, Long userId);
}
