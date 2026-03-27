package com.project.board.main.api.service.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class DiscordService {
    private static final int MAX_DISCORD_MESSAGE_LENGTH = 2000;

    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${discord.webhookUrl}")
    private String webhookUrl;

    public CompletableFuture<Void> discordSend(String content) {
        return CompletableFuture.runAsync(() -> {
            List<String> splitMessages = splitMessage(content);
            for (String message : splitMessages) {
                try {
                    sendMessage(message);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException("Failed to send Discord message", e);
                }
            }
        });
    }

    private void sendMessage(String content) throws IOException {
        RequestBody body = RequestBody.create(
                objectMapper.writeValueAsString(new DiscordMessage(content)),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(webhookUrl)
                .post(body)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    private List<String> splitMessage(String message) {
        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < message.length()) {
            int endIndex = Math.min(index + MAX_DISCORD_MESSAGE_LENGTH, message.length());

            if (endIndex < message.length()) {
                int lastNewLine = message.lastIndexOf('\n', endIndex);
                if (lastNewLine > index) {
                    endIndex = lastNewLine + 1;
                }
            }

            result.add(message.substring(index, endIndex));
            index = endIndex;
        }
        return result;
    }

    private static class DiscordMessage {
        private final String content;

        public DiscordMessage(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}
