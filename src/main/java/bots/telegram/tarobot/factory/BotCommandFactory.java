package bots.telegram.tarobot.factory;

import bots.telegram.tarobot.commands.*;
import bots.telegram.tarobot.util.enums.ButtonText;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
public class BotCommandFactory {
    private final StartCommand startCommand;
    private final CardLayoutCommand cardLayoutCommand;
    private final AboutLayoutCommand aboutLayoutCommand;
    private final EditAboutCommand editAboutCommand;
    private final CancelCommand cancelCommand;

    public TaroBotCommand getBotCommand(String command, Message message) {
        return switch (command) {
            case "/start" -> startCommand;
            case ButtonText.TAROT_READING -> cardLayoutCommand;
            case ButtonText.INFO -> aboutLayoutCommand;
            case ButtonText.CHANGE_DATA -> editAboutCommand;
            case ButtonText.CANCEL -> cancelCommand;
            default -> null;
        };
    }
}
