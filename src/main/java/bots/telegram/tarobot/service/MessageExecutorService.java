package bots.telegram.tarobot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Service
public class MessageExecutorService {
    String botToken;
    TelegramClient telegramClient;

    public MessageExecutorService() {
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
