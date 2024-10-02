package bots.telegram.tarobot.service;

import bots.telegram.tarobot.model.RequestRepository;
import bots.telegram.tarobot.model.UserRepository;
import bots.telegram.tarobot.util.enums.BotMessage;
import bots.telegram.tarobot.util.enums.ButtonText;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataDistributionService {
    private final UserService userService;
    private final RequestService requestService;
    private final MessageExecutorService messageExecutorService;
    private final KeyboardService keyboardService;
    private final CardLayoutService cardLayoutService;

    public void distribute(Message message) {
        var user = userService.findByTelegramId(message.getFrom().getId());
        if (user != null) {
            if (user.getAbout() == null) {
                user.setAbout(message.getText());
                userService.save(user);
                sendSuccessRegistrationMessage(message.getFrom().getId());
            } else {
                var lastRequest = requestService.findTop1ByUserOrderByTimestampDesc(user);
                if (lastRequest != null && lastRequest.getRequest() == null) {
                    lastRequest.setRequest(message.getText());
                    requestService.save(lastRequest);
                    cardLayoutService.beginLayout(message);
                }
            }
        } else {
            sendWrongRequestMessage(message.getChatId());
        }
    }

    private void sendSuccessRegistrationMessage(Long chatId) {
        SendMessage message = SendMessage.builder()
                .text(BotMessage.SUCCESS_REGISTRATION)
                .chatId(chatId)
                .replyMarkup(makeSuccessRegistrationReplyKeyboard())
                .build();
        messageExecutorService.execute(message);
    }

    private ReplyKeyboardMarkup makeSuccessRegistrationReplyKeyboard() {
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

    private void sendWrongRequestMessage(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.WRONG_COMMAND)
                .build();
        messageExecutorService.execute(message);
    }
}
