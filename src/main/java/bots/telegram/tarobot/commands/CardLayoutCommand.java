package bots.telegram.tarobot.commands;

import bots.telegram.tarobot.service.MessageExecutorService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
public class CardLayoutCommand extends TaroBotCommand {
    MessageExecutorService messageExecutorService;

    @Override
    public void processCommand(Message message) {

    }
}
