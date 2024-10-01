package bots.telegram.tarobot.controller;

import bots.telegram.tarobot.commands.TaroBotCommand;
import bots.telegram.tarobot.factory.BotCommandFactory;
import bots.telegram.tarobot.service.DataDistributionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class TaroTelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    BotCommandFactory botCommandFactory;
    DataDistributionService dataDistributionService;

    private @Value("${telegram.bot.token}") String botToken;

    public TaroTelegramBot(BotCommandFactory botCommandFactory,
                           @Value("${telegram.bot.token}") String botToken,
                           DataDistributionService dataDistributionService) {
        this.botCommandFactory = botCommandFactory;
        this.dataDistributionService = dataDistributionService;
    }


    @Override
    public String getBotToken() {
        return botToken;
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
            } else {
                dataDistributionService.distribute(update.getMessage());
            }
        }
    }
}
