package bots.telegram.tarobot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

@Slf4j
@Service
public class MessageExecutorService {
    TelegramClient telegramClient;
    private @Value("${telegram.bot.token}") String botToken;

    public MessageExecutorService(@Value("${telegram.bot.token}") String botToken) {
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    public Message execute(SendMessage message) {
        try {
            return telegramClient.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending message. Error: " + e.getMessage());
        }
        return null;
    }

    public List<Message> execute(SendMediaGroup mediaGroup) {
        try {
            return telegramClient.execute(mediaGroup);
        } catch (TelegramApiException e) {
            log.error("Error sending media group. Error: " + e.getMessage());
        }
        return null;
    }

    public boolean execute(DeleteMessage deleteMessage) {
        try {
            return telegramClient.execute(deleteMessage);
        } catch (TelegramApiException e) {
            log.error("Error sending media group. Error: " + e.getMessage());
        }
        return false;
    }
}
