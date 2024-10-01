package bots.telegram.tarobot.controller;

import bots.telegram.tarobot.commands.TaroBotCommand;
import bots.telegram.tarobot.factory.BotCommandFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaroTelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    UserController userController;
    BotCommandFactory botCommandFactory;

    @Override
    public String getBotToken() {
        return null; // TODO add bot token
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            TaroBotCommand command = botCommandFactory.getBotCommand(update.getMessage().getText(), update.getMessage());
            if (command != null) {
                command.process(update.getMessage());
            }
        }
    }
}
