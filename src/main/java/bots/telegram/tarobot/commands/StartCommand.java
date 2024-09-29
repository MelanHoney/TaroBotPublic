package bots.telegram.tarobot.commands;

import bots.telegram.tarobot.service.KeyboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class StartCommand extends BotCommand<SendMessage> {
    KeyboardService keyboardService;

    @Override
    public Stream<SendMessage> getResponse(Message message) {
        // TODO user registration
        return Stream.of(makeMessage(message));
    }

    private SendMessage makeMessage(Message message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(message.getChatId())
                .text(startMessageText)
                .replyMarkup(makeInlineKeyboard())
                .build();
        // TODO return registration message
        return 0;
    }

    private InlineKeyboardMarkup makeInlineKeyboard() {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(keyboardService.makeInlineKeyboardButton(buttonText, callbackData))
                .build();
    }
}
