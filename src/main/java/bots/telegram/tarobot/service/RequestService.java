package bots.telegram.tarobot.service;

import bots.telegram.tarobot.model.Request;
import bots.telegram.tarobot.model.RequestRepository;
import bots.telegram.tarobot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestService {
    RequestRepository requestRepository;

    public Request findTop1ByUserOrderByTimestampDesc(User user) {
        return requestRepository.findTop1ByUserOrderByTimestampDesc(user);
    }

    public Request save(Request lastRequest) {
        return requestRepository.save(lastRequest);
    }
}
