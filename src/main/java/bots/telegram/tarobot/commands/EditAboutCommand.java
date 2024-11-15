package bots.telegram.tarobot.commands;

import bots.telegram.tarobot.service.KeyboardService;
import bots.telegram.tarobot.service.MessageExecutorService;
import bots.telegram.tarobot.service.UserService;
import bots.telegram.tarobot.util.enums.BotMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
@RequiredArgsConstructor
public class EditAboutCommand extends TaroBotCommand{
    private final MessageExecutorService messageExecutorService;
    private final UserService userService;
    private final KeyboardService keyboardService;

    @Override
    public void process(Message message) {
        askToWriteNewAbout(message.getChatId());
        var user = userService.getByTelegramId(message.getFrom().getId());
        user.setAbout(null);
        userService.save(user);

    }

    private void askToWriteNewAbout(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.UPDATE_ABOUT)
                .replyMarkup(new ReplyKeyboardRemove(true))
                .build();
        messageExecutorService.execute(message);
    }
}
