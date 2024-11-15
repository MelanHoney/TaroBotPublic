package bots.telegram.tarobot.commands;

import bots.telegram.tarobot.model.Request;
import bots.telegram.tarobot.service.KeyboardService;
import bots.telegram.tarobot.service.MessageExecutorService;
import bots.telegram.tarobot.service.RequestService;
import bots.telegram.tarobot.service.UserService;
import bots.telegram.tarobot.util.enums.BotMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
@RequiredArgsConstructor
public class CardLayoutCommand extends TaroBotCommand {
    private final KeyboardService keyboardService;
    private final MessageExecutorService messageExecutorService;
    private final UserService userService;
    private final RequestService requestService;

    @Override
    public void process(Message message) {
        var user = userService.findByTelegramId(message.getFrom().getId());
        if (user != null && user.getAbout() != null) {
            var lastRequest = requestService.findTop1ByUserOrderByTimestampDesc(user);
            if (lastRequest != null
                    && lastRequest.getResponse() != null
                    && (lastRequest.getResponse().equals("error") || lastRequest.getResponse().equals("gemini error"))) {
                askUserToWriteContext(message.getChatId());
            } else {
                requestService.save(Request.builder().user(user).build());
                askUserToWriteContext(message.getChatId());
            }
        } else {
            askUserToRegister(message.getChatId());
        }
    }

    private void askUserToWriteContext(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.ASK_CONTEXT)
                .replyMarkup(keyboardService.getCancelButtonKeyboard())
                .build();
        messageExecutorService.execute(message);
    }

    private void askUserToRegister(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.NOT_REGISTERED)
                .build();
        messageExecutorService.execute(message);
    }
}
