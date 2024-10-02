package bots.telegram.tarobot.commands;

import bots.telegram.tarobot.model.Request;
import bots.telegram.tarobot.model.UserRepository;
import bots.telegram.tarobot.service.MessageExecutorService;
import bots.telegram.tarobot.service.RequestService;
import bots.telegram.tarobot.service.UserService;
import bots.telegram.tarobot.util.enums.BotMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
public class CardLayoutCommand extends TaroBotCommand {
    private final MessageExecutorService messageExecutorService;
    private final UserService userService;
    private final RequestService requestService;

    @Override
    public void process(Message message) {
        var user = userService.findByTelegramId(message.getFrom().getId());
        if (user != null && user.getAbout() != null) {
            requestService.save(Request.builder().user(user).build());
            askUserToWriteContext(message.getChatId());
        } else {
            askUserToRegister(message.getChatId());
        }
    }

    private void askUserToWriteContext(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.ASK_CONTEXT)
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
