package bots.telegram.tarobot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardLayoutService {
    Random random = new Random();

    public void beginLayout(Message message) {
        randomizeThreeCards();
        transferDataTOGemini();
        getGeminiResponse();
        sendResponseToUser();
    }

    private void randomizeThreeCards() {
        random.nextInt();
        // TODO cards randomize
    }
}
