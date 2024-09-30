package bots.telegram.tarobot.controller;

import bots.telegram.tarobot.model.UserRepository;
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

@Component
@RequiredArgsConstructor
public class UserController {
    UserRepository userRepository;
    KeyboardService keyboardService;

    public boolean isAboutRequired(Long telegramId) {
        if (userRepository.existsByTelegramId(telegramId)) {
            var user = userRepository.getByTelegramId(telegramId);
            return user.getAbout().isEmpty();
        }
        return false;
    }

    public SendMessage addAboutDataFromMessage(Message message) {
        var user = userRepository.getByTelegramId(message.getFrom().getId());
        user.setAbout(message.getText());
        userRepository.save(user);
        return SendMessage.builder()
                .text(BotMessage.SUCCESS_REGISTRATION)
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
