package com.business.slackservice.application.service.v2;

import com.business.slackservice.application.dto.v2.request.slack.ReqSlackPostDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slack.ResSlackGetByIdDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slack.ResSlackGetDTOApiV2;
import com.business.slackservice.application.dto.v2.response.slack.ResSlackPostDTOApiV2;
import com.querydsl.core.types.Predicate;
import com.slack.api.methods.SlackApiException;
import java.io.IOException;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface SlackServiceApiV2 {
    ResSlackPostDTOApiV2 postBy(ReqSlackPostDTOApiV2 dto) throws SlackApiException, IOException;

    ResSlackGetByIdDTOApiV2 getBy(UUID id);

    ResSlackGetDTOApiV2 getBy(Predicate predicate, Pageable pageable);

    void deleteBy(UUID id, Long userId);
}
