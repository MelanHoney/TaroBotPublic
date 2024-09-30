package bots.telegram.tarobot.commands;

import org.telegram.telegrambots.meta.api.objects.message.Message;

public abstract class TaroBotCommand {
    public abstract void processCommand(Message message);
}
