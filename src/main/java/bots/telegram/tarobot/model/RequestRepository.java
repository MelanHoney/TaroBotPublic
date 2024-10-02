package bots.telegram.tarobot.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<Request,Long> {
    Request findTop1ByUserOrderByTimestampDesc(User user);
}
