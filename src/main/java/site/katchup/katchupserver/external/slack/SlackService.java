package site.katchup.katchupserver.external.slack;


import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class SlackService {

    @Value("${slack.bot-token}")
    private String SLACK_TOKEN;

    private final Environment env;

    public void sendMessage(String channel, String text) {
        try {
            Slack slack = Slack.getInstance();
            ChatPostMessageResponse response = slack.methods(SLACK_TOKEN).chatPostMessage(req -> req
                    .channel(channel)
                    .text("[" + getProfiles() + "]" + text));
            System.out.println(response);
        } catch (IOException | SlackApiException e) {
            throw new RuntimeException(e);
        }
    }

    private String getProfiles() {
        return Arrays.stream(env.getActiveProfiles()).findFirst().orElse("");
    }
}
