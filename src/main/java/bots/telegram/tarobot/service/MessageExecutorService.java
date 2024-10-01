package bots.telegram.tarobot.service;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Service
public class MessageExecutorService {
    TelegramClient telegramClient;
    private @Value("${telegram.bot.token}") String botToken;

    public MessageExecutorService(@Value("${telegram.bot.token}") String botToken) {
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    public void execute(SendMessage message) {
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending message. Error: " + e.getMessage());
        }
    }
}
