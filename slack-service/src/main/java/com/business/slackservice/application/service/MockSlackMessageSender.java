package com.business.slackservice.application.service;

import com.business.slackservice.domain.slack.vo.SlackStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("performance")
public class MockSlackMessageSender implements SlackMessageSender {

    @Override
    public SlackStatus send(String slackId, String content) {

        return SlackStatus.SENT;
    }
}
