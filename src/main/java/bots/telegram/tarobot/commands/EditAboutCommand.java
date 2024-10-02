package bots.telegram.tarobot.commands;

import bots.telegram.tarobot.service.MessageExecutorService;
import bots.telegram.tarobot.util.enums.BotMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
public class EditAboutCommand extends TaroBotCommand{
    MessageExecutorService messageExecutorService;

    @Override
    public void process(Message message) {
        askToWriteNewAbout(message.getChatId());
    }

    private void askToWriteNewAbout(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.UPDATE_ABOUT)
                .build();
        messageExecutorService.execute(message);
    }
}
