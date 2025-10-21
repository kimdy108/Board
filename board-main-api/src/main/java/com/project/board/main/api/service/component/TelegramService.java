package com.project.board.main.api.service.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class TelegramService extends TelegramLongPollingBot {
    @Value("${telegram.chatId}")
    private String chatId;

    @Value("${telegram.botToken}")
    private String botToken;

    private final LoggerService loggerService;

    @Override
    public String getBotUsername() {
        return chatId;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        loggerService.writeLogger("info", update.getMessage().getText());
    }

    public void telegramSend(String message) {
        try {
            execute(SendMessage.builder()
                    .chatId(getBotUsername())
                    .text(message)
                    .build());
        } catch (TelegramApiException e) {
            loggerService.writeLogger("error", e.getMessage());
            e.printStackTrace();
        }
    }
}
