package bots.telegram.tarobot.commands;

import bots.telegram.tarobot.service.KeyboardService;
import bots.telegram.tarobot.util.enums.BotMessage;
import bots.telegram.tarobot.util.enums.ButtonText;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
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
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(BotMessage.START)
                .replyMarkup(makeReplyKeyboard())
                .build();
    }

    private ReplyKeyboardMarkup makeReplyKeyboard() {
        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(
                        new KeyboardRow(List.of(
                                keyboardService.makeKeyboardButton(ButtonText.TAROT_READING),
                                keyboardService.makeKeyboardButton(ButtonText.INFO)
                        )),
                        new KeyboardRow(List.of(
                                keyboardService.makeKeyboardButton(ButtonText.CHANGE_DATA)
                        ))
                ))
                .build();
    }
}
