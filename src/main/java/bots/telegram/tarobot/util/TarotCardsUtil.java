package bots.telegram.tarobot.util;

import bots.telegram.tarobot.util.enums.TarotCard;
import lombok.Getter;

import java.util.HashMap;
import java.util.Random;

@Getter
public class TarotCardsUtil {
    private static HashMap<Integer, TarotCard> cards;
    private static Random rand;

    private TarotCardsUtil() {
        for (TarotCard card : TarotCard.values()) {
            cards.put(card.ordinal(), card);
        }
        rand = new Random();
    }

    public static HashMap<Integer, TarotCard> getRandomThreeCards() {
        HashMap<Integer, TarotCard> randomThreeCards = new HashMap<>();

        for (int i = 0; i < 3; i++) {
            Integer randomCardIndex = rand.nextInt(78);
            randomThreeCards.put(i, cards.get(randomCardIndex));
        }

        return randomThreeCards;
    }
}
