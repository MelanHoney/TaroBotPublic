package bots.telegram.tarobot.commands;

import bots.telegram.tarobot.service.MessageExecutorService;
import bots.telegram.tarobot.util.enums.BotMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
public class AboutLayoutCommand extends TaroBotCommand{
    private final MessageExecutorService messageExecutorService;

    @Override
    public void process(Message message) {
        sendAboutLayoutMessage(message.getChatId());
    }

    private void sendAboutLayoutMessage(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.ABOUT_LAYOUT)
                .build();
        messageExecutorService.execute(message);
    }
}
