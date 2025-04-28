package com.business.slackservice.application.service.v2;

import com.business.slackservice.application.dto.v2.request.slackEventType.ReqSlackEventTypePostDTOApiV2;
import com.business.slackservice.application.dto.v2.request.slackEventType.ReqSlackEventTypePutDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackEventType.ResSlackEventTypeGetByIdDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackEventType.ResSlackEventTypeGetDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slackEventType.ResSlackEventTypePostDTOApiV2;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface SlackEventTypeServiceApiV2 {

    ResSlackEventTypePostDTOApiV2 postBy(@Valid ReqSlackEventTypePostDTOApiV2 dto);

    ResSlackEventTypeGetByIdDTOApiV2 getBy(UUID id);

    ResSlackEventTypeGetDTOApiV2 getBy(Predicate predicate, Pageable pageable);

    void putBy(UUID id, @Valid ReqSlackEventTypePutDTOApiV2 dto);

    void deleteBy(UUID id, Long userId);
}
