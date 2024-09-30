package bots.telegram.tarobot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

@Service
@RequiredArgsConstructor
public class KeyboardService {
    public KeyboardButton makeKeyboardButton(String text) {
        return KeyboardButton.builder()
                .text(text)
                .build();
    }
}
