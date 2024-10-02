package bots.telegram.tarobot.service;

import bots.telegram.tarobot.util.TarotCardsUtil;
import bots.telegram.tarobot.util.enums.TarotCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CardLayoutService {
    private final UserService userService;

    public void beginLayout(Message message) {
        HashMap<Integer, TarotCard> cards = TarotCardsUtil.getRandomThreeCards();
        String mergedData = mergeUserAboutAndRequest(message);
        String response = geminiService.getResponse(cards, mergedData);
        sendResponseToUser();
    }

    private String mergeUserAboutAndRequest(Message message) {
        return userService.getByTelegramId(message.getFrom().getId()).getAbout() + "\n" + message.getText();
    }
}
