package bots.telegram.tarobot.service;

import bots.telegram.tarobot.model.RequestRepository;
import bots.telegram.tarobot.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class TextDataDistributionService {
    UserRepository userRepository;
    RequestRepository requestRepository;

    public SendMessage processMessage(Message message) {
        var user = userRepository.findByTelegramId(message.getFrom().getId());
        if (user != null) {
            if (user.getAbout() == null) {
                user.setAbout(message.getText());
                userRepository.save(user);
            } else {

            }
        }

        return new SendMessage();
    }
}
