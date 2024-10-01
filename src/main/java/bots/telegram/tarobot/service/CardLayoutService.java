package bots.telegram.tarobot.service;

import bots.telegram.tarobot.util.TarotCardsUtil;
import bots.telegram.tarobot.util.enums.TarotCard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.HashMap;
import java.util.Random;

@Service
public class CardLayoutService {
    HashMap<Integer, TarotCard> cards;
    Random random;

    public CardLayoutService() {
        this.cards = TarotCardsUtil.getCards();
        this.random = new Random();
    }

    public void beginLayout(Message message) {
        randomizeThreeCards();
        transferDataToGemini();
        getGeminiResponse();
        sendResponseToUser();
    }

    private void randomizeThreeCards() {
        random.nextInt();
        // TODO cards randomize
    }
}
