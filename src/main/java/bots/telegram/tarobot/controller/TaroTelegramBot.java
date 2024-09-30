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
    UserController userController;
    BotCommandFactory botCommandFactory;

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            BotCommand command = botCommandFactory.getBotCommand(update.getMessage().getText(), update.getMessage());
            if (command != null) {
                Stream<PartialBotApiMethod> response = command.getResponse(update.getMessage());
                response.forEach(this::sendResponse);
            } else if (userController.isAboutRequired(update.getMessage().getFrom().getId())) {
                userController.addAboutDataFromMessage(update.getMessage());
            }

        }
    }

    private void sendResponse(PartialBotApiMethod message) {
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending media group. Error: " + e.getMessage());
        }
    }
}
