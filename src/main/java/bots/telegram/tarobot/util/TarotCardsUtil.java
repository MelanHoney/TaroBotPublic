package bots.telegram.tarobot.util;

import bots.telegram.tarobot.util.enums.TarotCard;
import lombok.Getter;

import java.util.HashMap;
import java.util.Random;

@Getter
public class TarotCardsUtil {
    private static final HashMap<Integer, TarotCard> cards = new HashMap<>();
    private static final Random rand;

    // Статический блок для инициализации карт
    static {
        for (TarotCard card : TarotCard.values()) {
            cards.put(card.ordinal(), card);
        }
        rand = new Random();
    }

    public static HashMap<Integer, TarotCard> getRandomThreeCards() {
        HashMap<Integer, TarotCard> randomThreeCards = new HashMap<>();

        for (int i = 0; i < 3; i++) {
            Integer randomCardIndex = rand.nextInt(cards.size());
            // Проверяем, что карта существует
            TarotCard card = cards.get(randomCardIndex);
            if (card != null) {
                randomThreeCards.put(i, card);
            } else {
                // Обработка случая, когда карта не найдена (опционально)
                throw new IllegalStateException("Карта с индексом " + randomCardIndex + " не найдена.");
            }
        }

        return randomThreeCards;
    }
}
