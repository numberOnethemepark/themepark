package com.business.slackservice.application.service.v1;

import com.business.slackservice.application.dto.v1.request.slack.ReqSlackPostDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slack.ResSlackGetByIdDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slack.ResSlackGetDTOApiV1;
import com.business.slackservice.application.dto.v1.response.slack.ResSlackPostDTOApiV1;
import com.querydsl.core.types.Predicate;
import com.slack.api.methods.SlackApiException;
import java.io.IOException;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface SlackServiceApiV1 {
    ResSlackPostDTOApiV1 postBy(ReqSlackPostDTOApiV1 dto) throws SlackApiException, IOException;

    ResSlackGetByIdDTOApiV1 getBy(UUID id);

    ResSlackGetDTOApiV1 getBy(Predicate predicate, Pageable pageable);

    void deleteBy(UUID id, Long userId);
}
