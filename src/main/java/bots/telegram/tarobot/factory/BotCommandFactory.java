package bots.telegram.tarobot.factory;

import bots.telegram.tarobot.commands.CardLayoutCommand;
import bots.telegram.tarobot.commands.StartCommand;
import bots.telegram.tarobot.commands.TaroBotCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
public class BotCommandFactory {
    private final StartCommand startCommand;
    private final CardLayoutCommand cardLayoutCommand;

    public TaroBotCommand getBotCommand(String command, Message message) {
        return switch (command) {
            case "/start" -> startCommand;
            case "\uD83D\uDD2EПолучить расклад" -> cardLayoutCommand;
            default -> null;
        };
    }
}
