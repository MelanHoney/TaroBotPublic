package bots.telegram.tarobot.service;

import bots.telegram.tarobot.model.RequestRepository;
import bots.telegram.tarobot.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

/** !!!!!!!!!!!!!!!!!
// TODO Это ваще пока не трогать, тут полный калик
// !!!!!!!!!!!!!!!!! */

@Service
@RequiredArgsConstructor
public class DataDistributionService {
    UserRepository userRepository;
    RequestRepository requestRepository;

    public void distribute(Message message) {
        var user = userRepository.findByTelegramId(message.getFrom().getId());
        if (user != null) {
            if (user.getAbout() == null) {
                user.setAbout(message.getText());
                userRepository.save(user);
            } else {

            }
        } else {

        }

    }
}