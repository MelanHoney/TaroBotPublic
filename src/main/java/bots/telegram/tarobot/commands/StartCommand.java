package bots.telegram.tarobot.commands;

import bots.telegram.tarobot.model.User;
import bots.telegram.tarobot.model.UserRepository;
import bots.telegram.tarobot.service.MessageExecutorService;
import bots.telegram.tarobot.util.enums.BotMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
public class StartCommand extends TaroBotCommand {
    UserRepository userRepository;
    MessageExecutorService messageExecutorService;

    @Override
    public void processCommand(Message message) {
        var user = userRepository.findByTelegramId(message.getFrom().getId());
        if (user == null) {
            registerUser(message.getFrom());
            sendSuccessRegistrationMessage(message.getChatId());
        } else {
            sendAlreadyRegisteredMessage(message.getChatId());
        }
    }


    private void registerUser(org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        User user = User.builder()
                .telegramId(telegramUser.getId())
                .username(telegramUser.getUserName())
                .build();
        userRepository.save(user);
    }

    private void sendSuccessRegistrationMessage(Long chatId) {
        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.START)
                .build();
        messageExecutorService.execute(sendMessage);
    }

    private void sendAlreadyRegisteredMessage(Long chatId) {
        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.ALREADY_REGISTERED)
                .build();
        messageExecutorService.execute(sendMessage);
    }
}
