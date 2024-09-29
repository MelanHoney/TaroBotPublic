package bots.telegram.tarobot.factory;

import bots.telegram.tarobot.commands.BotCommand;
import bots.telegram.tarobot.commands.StartCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class BotCommandFactory {
    private final StartCommand startCommand;

    public BotCommand getBotCommand(String command, Message message) {
        switch (command) {
            case "/start": return startCommand;

        }
    }
}
