package bots.telegram.tarobot.controller;

import bots.telegram.tarobot.commands.BotCommand;
import bots.telegram.tarobot.factory.BotCommandFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaroTelegramBot extends TelegramLongPollingBot {
    BotCommandFactory botCommandFactory;

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {
        BotCommand command = botCommandFactory.getBotCommand(update.getMessage().getText(), update.getMessage());
        Stream<PartialBotApiMethod> response = command.getResponse(update.getMessage());
        response.forEach(this::sendResponse);
    }

    private void sendResponse(PartialBotApiMethod message) {
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending media group. Error: " + e.getMessage());
        }
    }
}
