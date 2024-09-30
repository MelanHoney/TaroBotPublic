package bots.telegram.tarobot.model;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByTelegramId(Long telegramId);
}
