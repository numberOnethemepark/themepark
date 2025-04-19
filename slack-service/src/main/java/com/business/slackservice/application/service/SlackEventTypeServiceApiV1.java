package com.business.slackservice.application.service;

import com.business.slackservice.application.dto.request.slackEventType.ReqSlackEventTypePostDTOApiV1;
import com.business.slackservice.application.dto.request.slackEventType.ReqSlackEventTypePutDTOApiV1;
import com.business.slackservice.application.dto.response.slackEventType.ResSlackEventTypeGetByIdDTOV1;
import com.business.slackservice.application.dto.response.slackEventType.ResSlackEventTypeGetDTOV1;
import com.business.slackservice.application.dto.response.slackEventType.ResSlackEventTypePostDTOApiV1;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface SlackEventTypeServiceApiV1 {

    ResSlackEventTypePostDTOApiV1 postBy(@Valid ReqSlackEventTypePostDTOApiV1 dto);

    ResSlackEventTypeGetByIdDTOV1 getBy(UUID id);

    ResSlackEventTypeGetDTOV1 getBy(Predicate predicate, Pageable pageable);

    void putBy(UUID id, @Valid ReqSlackEventTypePutDTOApiV1 dto);

    void deleteBy(UUID id, Long userId);
}
