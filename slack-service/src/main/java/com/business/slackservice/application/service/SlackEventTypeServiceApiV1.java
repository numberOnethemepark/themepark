package com.business.slackservice.application.service;

import com.business.slackservice.application.dto.request.slackEventType.ReqSlackEventTypePostDTOApiV1;
import com.business.slackservice.application.dto.request.slackEventType.ReqSlackEventTypePutDTOApiV1;
import com.business.slackservice.application.dto.response.slackEventType.ResSlackEventTypeGetByIdDTOApiV1;
import com.business.slackservice.application.dto.response.slackEventType.ResSlackEventTypeGetDTOApiV1;
import com.business.slackservice.application.dto.response.slackEventType.ResSlackEventTypePostDTOApiV1;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface SlackEventTypeServiceApiV1 {

    ResSlackEventTypePostDTOApiV1 postBy(@Valid ReqSlackEventTypePostDTOApiV1 dto);

    ResSlackEventTypeGetByIdDTOApiV1 getBy(UUID id);

    ResSlackEventTypeGetDTOApiV1 getBy(Predicate predicate, Pageable pageable);

    void putBy(UUID id, @Valid ReqSlackEventTypePutDTOApiV1 dto);

    void deleteBy(UUID id, Long userId);
}
