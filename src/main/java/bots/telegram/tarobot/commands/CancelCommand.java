package bots.telegram.tarobot.commands;

import bots.telegram.tarobot.service.KeyboardService;
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
public class CancelCommand extends TaroBotCommand {
    private final UserService userService;
    private final RequestService requestService;
    private final KeyboardService keyboardService;
    private final MessageExecutorService messageExecutorService;

    @Override
    public void process(Message message) {
        deleteRequest(message.getFrom().getId());
        sendOperationCancelledMessage(message.getChatId());
    }

    private void deleteRequest(Long userId) {
        var user = userService.getByTelegramId(userId);
        var request = requestService.findTop1ByUserOrderByTimestampDesc(user);
        requestService.delete(request);
    }

    private void sendOperationCancelledMessage(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.LAYOUT_CANCELED)
                .replyMarkup(keyboardService.getReplyKeyboardMarkup())
                .build();
        messageExecutorService.execute(message);
    }
}
