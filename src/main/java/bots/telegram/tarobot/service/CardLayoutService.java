package bots.telegram.tarobot.service;

import bots.telegram.tarobot.util.TarotCardsUtil;
import bots.telegram.tarobot.util.enums.TarotCard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.HashMap;

@Service
public class CardLayoutService {

    public void beginLayout(Message message) {
        HashMap<Integer, TarotCard> cards = TarotCardsUtil.getRandomThreeCards();
        transferDataToGemini();
        getGeminiResponse();
        sendResponseToUser();
    }
}
