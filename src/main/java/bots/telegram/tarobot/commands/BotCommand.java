package bots.telegram.tarobot.commands;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.stream.Stream;

public abstract class BotCommand<T extends PartialBotApiMethod> {
    public abstract Stream<T> getResponse(Message message);
}
