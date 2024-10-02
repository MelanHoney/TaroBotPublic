package bots.telegram.tarobot.service;

import bots.telegram.tarobot.model.User;
import bots.telegram.tarobot.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean existsByTelegramId(Long telegramId) {
        return userRepository.existsByTelegramId(telegramId);
    }

    public User findByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId);
    }

    public User getByTelegramId(Long telegramId) {
        return userRepository.getByTelegramId(telegramId);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
