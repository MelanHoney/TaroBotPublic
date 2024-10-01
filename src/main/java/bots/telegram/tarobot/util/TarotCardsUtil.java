package bots.telegram.tarobot.util;

import bots.telegram.tarobot.util.enums.TarotCard;

import java.util.HashMap;

public class TarotCardsUtil {
    public static HashMap<Integer, TarotCard> getCards() {
        HashMap<Integer, TarotCard> cards = new HashMap<>();

        for (TarotCard card : TarotCard.values()) {
            cards.put(card.ordinal(), card);
        }

        return cards;
    }
}
