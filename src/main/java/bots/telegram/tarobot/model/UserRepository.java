package bots.telegram.tarobot.model;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByTelegramId(Long telegramId);
    User getByTelegramId(Long telegramId);
    void updateByTelegramId(Long telegramId);
}
