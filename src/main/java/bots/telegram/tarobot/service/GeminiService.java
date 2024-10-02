package bots.telegram.tarobot.service;

import bots.telegram.tarobot.util.enums.TarotCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class GeminiService {
    @Value("${gemini.token}") String geminiToken;

    // Получает запрос от гемини POST
    public String getResponse(HashMap<Integer, TarotCard> cards, String mergedData){
        List<String> cardList = new ArrayList<>();

        for (TarotCard card : cards.values()) {
            cardList.add(card.getName());
        }

        String prompt = "Привет, давай поиграем в игру. Представь, что ты - гадалка таро. Вот карты, которые " +
                "тебе выпали: %s, %s, %s. \n\nВот запрос и информация о себе: %s"
                        .formatted(cardList.get(0), cardList.get(1), cardList.get(2), mergedData);

        System.out.println(prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", );



        return prompt;
    }
}
