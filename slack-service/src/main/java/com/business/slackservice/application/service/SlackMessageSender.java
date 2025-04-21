package com.business.slackservice.application.service;

import com.business.slackservice.domain.slack.vo.SlackStatus;
import com.slack.api.methods.SlackApiException;
import java.io.IOException;

public interface SlackMessageSender {
    SlackStatus send(String slackId, String content) throws SlackApiException, IOException;
}
