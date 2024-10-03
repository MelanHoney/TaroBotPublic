package bots.telegram.tarobot.service;

import bots.telegram.tarobot.util.enums.TarotCard;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class GeminiService {
    private final @Value("${gemini.token}") String geminiToken;
    private final String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
    private final RestTemplate restTemplate = new RestTemplate();

    public GeminiService(@Value ("${gemini.token}")String geminiToken) {
        this.geminiToken = geminiToken;
    }

    // Получает запрос от гемини POST
    public String getResponse(HashMap<Integer, TarotCard> cards, String mergedData){
        List<String> cardList = new ArrayList<>();
        String result = new String();

        for (TarotCard card : cards.values()) {
            cardList.add(card.getName());
        }

        String prompt = ("Привет, ты - потомственная гадалка Инга. Ты гадаешь на Таро Уэйта и делаешь расклад (Три Карты). " +
                "Погадай, учитывая выпавшие карты и запрос. В сообщении выведи только гадание, без отсылки на это сообщение. " +
                "Опиши каждую выпавшую карту, и ее значение для человека, как именно она влияет на ответ, а также общий вывод. " +
                "\n\n Вот карты, которые тебе выпали: %s, %s, %s. \n\nВот запрос и информация о себе: %s\n\n" +
                        "Если с запросом что-то не так, и ты думаешь, что это не просьба погадать, а какая-то шутка или" +
                        " оскорбление, вместо сообщения напиши .Ошибка.")
                        .formatted(cardList.get(0), cardList.get(1), cardList.get(2), mergedData);

        System.out.println(prompt);

        // Заголовки
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", geminiToken);

        // Тело запроса
        HashMap<String, Object> requestBody = makeRequestBody(prompt);

        // Запрос
        HttpEntity<HashMap<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try{
            // Отправляем запрос
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl, HttpMethod.POST, entity, String.class);

            // Проверка ответа
            if (response.getStatusCode() == HttpStatus.OK) {
                String body = response.getBody();

                // Обрабатываем JSON-ответ
                JSONObject jsonObject = new JSONObject(body);
                JSONArray candidatesArray = jsonObject.getJSONArray("candidates");
                JSONObject firstCandidate = candidatesArray.getJSONObject(0);

                if (firstCandidate.has("content")) {
                    JSONObject content = firstCandidate.getJSONObject("content");
                    JSONArray partsArray = content.getJSONArray("parts");

                    result = partsArray.getJSONObject(0).getString("text");
                } else {
                    return "Ошибка, попробуй еще раз";
                }
            } else {
                return "Ошибка: Код ответа " + response.getStatusCode();
            }
        } catch (HttpClientErrorException | HttpServerErrorException e){
            System.out.println("Ошибка HTTP: " + e.getMessage());
        } catch (JSONException e){
            System.out.println("Ошибка парсинга JSON: " + e.getMessage());
        }

        return result;
    }

    private HashMap<String, Object> makeRequestBody(String prompt){
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(
                new HashMap<String, Object>() {{
                    put("parts", List.of(new HashMap<String, Object>() {{
                        put("text", prompt);
                    }}));
                }}
        ));

        return requestBody;
    }
}
