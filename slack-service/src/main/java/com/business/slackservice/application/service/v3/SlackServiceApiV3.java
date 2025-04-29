package com.business.slackservice.application.service.v3;

import com.business.slackservice.application.dto.v3.request.slack.ReqSlackPostDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slack.ResSlackGetByIdDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slack.ResSlackGetDTOApiV3;
import com.business.slackservice.application.dto.v3.response.slack.ResSlackPostDTOApiV3;
import com.querydsl.core.types.Predicate;
import com.slack.api.methods.SlackApiException;
import java.io.IOException;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface SlackServiceApiV3 {
    ResSlackPostDTOApiV3 postBy(ReqSlackPostDTOApiV3 dto) throws SlackApiException, IOException;

    ResSlackGetByIdDTOApiV3 getBy(UUID id);

    ResSlackGetDTOApiV3 getBy(Predicate predicate, Pageable pageable);

    void deleteBy(UUID id, Long userId);
}
