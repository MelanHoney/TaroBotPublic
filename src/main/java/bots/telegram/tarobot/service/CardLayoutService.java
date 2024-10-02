package bots.telegram.tarobot.service;

import bots.telegram.tarobot.util.TarotCardsUtil;
import bots.telegram.tarobot.util.enums.TarotCard;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CardLayoutService {
    private final UserService userService;
    private final RequestService requestService;
    private final MessageExecutorService messageExecutorService;

    public void beginLayout(Message message) {
        HashMap<Integer, TarotCard> cards = TarotCardsUtil.getRandomThreeCards();
        String mergedData = mergeUserAboutAndRequest(message);
        String response = geminiService.getResponse(cards, mergedData);
        saveResponse(message.getFrom().getId(), response);
        sendResponseToUser(message.getChatId(), response);
    }

    private String mergeUserAboutAndRequest(Message message) {
        return userService.getByTelegramId(message.getFrom().getId()).getAbout() + "\n" + message.getText();
    }

    private void saveResponse(@NonNull Long id, String response) {
        var user = userService.getByTelegramId(id);
        var request = requestService.findTop1ByUserOrderByTimestampDesc(user);
        request.setResponse(response);
        requestService.save(request);
    }

    private void sendResponseToUser(Long chatId, String response) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(response)
                .build();
        messageExecutorService.execute(message);
    }
}
