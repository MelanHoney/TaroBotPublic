package bots.telegram.tarobot.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TaroTelegramBot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
