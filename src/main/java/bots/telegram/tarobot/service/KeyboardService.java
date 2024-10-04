package bots.telegram.tarobot.service;

import bots.telegram.tarobot.util.enums.ButtonText;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyboardService {
    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(
                        new KeyboardRow(List.of(
                                makeKeyboardButton(ButtonText.TAROT_READING),
                                makeKeyboardButton(ButtonText.INFO)
                        )),
                        new KeyboardRow(List.of(
                                makeKeyboardButton(ButtonText.CHANGE_DATA)
                        ))
                ))
                .resizeKeyboard(true)
                .build();
    }

    public ReplyKeyboardMarkup getCancelButtonKeyboard() {
        return ReplyKeyboardMarkup.builder()
                .keyboardRow(new KeyboardRow(List.of(makeKeyboardButton(ButtonText.CANCEL))))
                .resizeKeyboard(true)
                .build();
    }

    public InlineKeyboardMarkup getInlineKeyboardMarkup(String url) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(makeInlineKeyboardButton(url)))
                .build();
    }

    private KeyboardButton makeKeyboardButton(String text) {
        return KeyboardButton.builder()
                .text(text)
                .build();
    }

    private InlineKeyboardButton makeInlineKeyboardButton(String url) {
        return InlineKeyboardButton.builder()
                .text("Оплатить расклад")
                .url(url)
                .build();
    }
}
