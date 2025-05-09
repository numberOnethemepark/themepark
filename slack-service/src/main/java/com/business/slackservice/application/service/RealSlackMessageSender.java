package com.business.slackservice.application.service;

import com.business.slackservice.domain.slack.vo.SlackStatus;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class RealSlackMessageSender implements SlackMessageSender {

    @Value("${slack.bot.token}")
    private String slackToken;

    @Override
    public SlackStatus send(String slackId, String content) throws SlackApiException, IOException {
        Slack slack = Slack.getInstance();

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
            .channel(slackId)
            .text(content)
            .build();

        ChatPostMessageResponse response = slack.methods(slackToken)
            .chatPostMessage(request);

        return response.isOk() ? SlackStatus.SENT : SlackStatus.FAILED;
    }
}
