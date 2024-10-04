package bots.telegram.tarobot.service;

import bots.telegram.tarobot.util.TarotCardsUtil;
import bots.telegram.tarobot.util.enums.BotMessage;
import bots.telegram.tarobot.util.enums.TarotCard;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableAsync
public class CardLayoutService {
    private final UserService userService;
    private final RequestService requestService;
    private final MessageExecutorService messageExecutorService;
    private final GeminiService geminiService;
    private final KeyboardService keyboardService;
    private final CardLayoutPreparingService cardLayoutPreparingService;

    public void beginLayout(Message message) {
        HashMap<Integer, TarotCard> randomThreeCards = TarotCardsUtil.getRandomThreeCards();
        CompletableFuture<List<Message>> messagesToDelete = cardLayoutPreparingService.sendPrepareMessages(message.getChatId(), randomThreeCards);
        String response = generateGeminiResponse(message, randomThreeCards);
        messagesToDelete.thenAccept((messages) -> {
            deletePreparingMessages(messages);
            if (response.equals(".Ошибка.")) {
                sendErrorResponse(message.getChatId());
            } else if (response.startsWith("Ошибка")) {
                sendGeminiErrorResponse(message.getChatId());
            } else {
                sendResponse(message.getChatId(), response);
            }
        });
    }

    private String generateGeminiResponse(Message message, HashMap<Integer, TarotCard> randomThreeCards) {
        String mergedData = mergeUserAboutAndRequest(message);
        String response = geminiService.getResponse(randomThreeCards, mergedData);

        if (response.startsWith("Ошибка")) {
            saveGeminiErrorResponse(message.getFrom().getId());
        } else if (response.equals(".Ошибка.")){
            saveErrorResponse(message.getFrom().getId());
        } else {
            saveResponse(message.getFrom().getId(), response);
        }

        return response;
    }

    private void deletePreparingMessages(List<Message> messages) {
        for (Message message : messages) {
            DeleteMessage deleteMessage = DeleteMessage.builder()
                    .chatId(message.getChatId())
                    .messageId(message.getMessageId())
                    .build();
            messageExecutorService.execute(deleteMessage);
        }
    }

    private String mergeUserAboutAndRequest(Message message) {
        var user = userService.getByTelegramId(message.getFrom().getId());
        return user.getAbout() + "\n" + requestService.findTop1ByUserOrderByTimestampDesc(user).getRequest();
    }

    private void saveGeminiErrorResponse(@NonNull Long id) {
        var user = userService.getByTelegramId(id);
        var request = requestService.findTop1ByUserOrderByTimestampDesc(user);
        request.setResponse("gemini error");
        requestService.save(request);
    }

    private void saveErrorResponse(@NonNull Long id) {
        var user = userService.getByTelegramId(id);
        var request = requestService.findTop1ByUserOrderByTimestampDesc(user);
        request.setResponse("error");
        requestService.save(request);
    }

    private void saveResponse(@NonNull Long id, String response) {
        var user = userService.getByTelegramId(id);
        var request = requestService.findTop1ByUserOrderByTimestampDesc(user);
        request.setResponse(response);
        requestService.save(request);
    }

    private void sendErrorResponse(Long chatId) {
        sendBeforeResultMessage(chatId);
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.WRONG_DATA)
                .replyMarkup(keyboardService.getReplyKeyboardMarkup())
                .build();
        messageExecutorService.execute(message);
    }

    private void sendGeminiErrorResponse(Long chatId) {
        sendBeforeResultMessage(chatId);
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.GEMINI_ERROR)
                .replyMarkup(keyboardService.getReplyKeyboardMarkup())
                .build();
        messageExecutorService.execute(message);
    }

    private void sendResponse(Long chatId, String response) {
        sendBeforeResultMessage(chatId);
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(response)
                .replyMarkup(keyboardService.getReplyKeyboardMarkup())
                .build();
        messageExecutorService.execute(message);
    }

    private void sendBeforeResultMessage(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.BEFORE_RESPONSE)
                .build();
        messageExecutorService.execute(message);
    }
}
