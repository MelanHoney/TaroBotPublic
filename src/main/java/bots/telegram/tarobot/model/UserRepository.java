package bots.telegram.tarobot.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByTelegramId(Long telegramId);
    User getByTelegramId(Long telegramId);
    User findByTelegramId(Long telegramId);
}
