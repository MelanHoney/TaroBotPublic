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
    UserRepository userRepository;
    RequestRepository requestRepository;
    MessageExecutorService messageExecutorService;
    KeyboardService keyboardService;

    public void distribute(Message message) {
        var user = userRepository.findByTelegramId(message.getFrom().getId());
        if (user != null) {
            if (user.getAbout() == null) {
                user.setAbout(message.getText());
                userRepository.save(user);
                sendSuccessRegistrationMessage(message.getChatId());
            } else {
                var lastRequest = requestRepository.findTop1ByUserOrderByTimestampDesc(user);
                if (lastRequest != null && lastRequest.getRequest() == null) {
                    lastRequest.setRequest(message.getText());
                    requestRepository.save(lastRequest);
                    startCardLayoutProcess();
                }
            }
        } else {
            sendWrongRequestMessage(message.getChatId());
        }
    }

    private void sendSuccessRegistrationMessage(Long chatId) {
        SendMessage message = SendMessage.builder()
                .text(BotMessage.SUCCESS_REGISTRATION)
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

    private void startCardLayoutProcess() {

    }

    private void sendWrongRequestMessage(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.WRONG_COMMAND)
                .build();
        messageExecutorService.execute(message);
    }
}
