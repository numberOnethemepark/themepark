package com.business.slackservice.application.service.v3;

import com.business.slackservice.application.dto.v3.request.slackEventType.ReqSlackEventTypePostDTOApiV3;
import com.business.slackservice.application.dto.v3.request.slackEventType.ReqSlackEventTypePutDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackEventType.ResSlackEventTypeGetByIdDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackEventType.ResSlackEventTypeGetDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slackEventType.ResSlackEventTypePostDTOApiV3;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface SlackEventTypeServiceApiV3 {

    ResSlackEventTypePostDTOApiV3 postBy(@Valid ReqSlackEventTypePostDTOApiV3 dto);

    ResSlackEventTypeGetByIdDTOApiV3 getBy(UUID id);

    ResSlackEventTypeGetDTOApiV3 getBy(Predicate predicate, Pageable pageable);

    void putBy(UUID id, @Valid ReqSlackEventTypePutDTOApiV3 dto);

    void deleteBy(UUID id, Long userId);
}
