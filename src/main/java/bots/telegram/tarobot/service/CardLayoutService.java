package bots.telegram.tarobot.service;

import bots.telegram.tarobot.util.TarotCardsUtil;
import bots.telegram.tarobot.util.enums.BotMessage;
import bots.telegram.tarobot.util.enums.TarotCard;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardLayoutService {
    private final UserService userService;
    private final RequestService requestService;
    private final MessageExecutorService messageExecutorService;
    private final GeminiService geminiService;
    private final KeyboardService keyboardService;

    public void beginLayout(Message message) {
        HashMap<Integer, TarotCard> randomThreeCards = TarotCardsUtil.getRandomThreeCards();
        CompletableFuture<List<Message>> messagesToDelete = sendPreparingMessages(message.getChatId(), randomThreeCards);
        String response = generateGeminiResponse(message, randomThreeCards);
        messagesToDelete.whenComplete((l, e) -> {
            deletePreparingMessages(l);
        });
        sendResponseToUser(message.getChatId(), response);
    }

    @Async
    protected CompletableFuture<List<Message>> sendPreparingMessages(Long chatId, HashMap<Integer, TarotCard> randomThreeCards) {
        try {
            sendBeforeCardsMessage(chatId);
            sendCardImagesWithDelay(chatId, randomThreeCards);
            List<Message> messagesToThenDelete = sendMessagesToThenDelete(chatId);

            TimeUnit.SECONDS.sleep(4);

            return CompletableFuture.completedFuture(messagesToThenDelete);
        } catch (InterruptedException e) {
            log.error("Error sending message: " + e.getMessage());
            return null;
        }
    }

    private void sendBeforeCardsMessage(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.BEFORE_CARDS)
                .build();
        messageExecutorService.execute(message);
    }

    private void sendCardImagesWithDelay(Long chatId, HashMap<Integer, TarotCard> randomThreeCards) {
        try {
            TimeUnit.SECONDS.sleep(5);

            SendMediaGroup cardImages = SendMediaGroup.builder()
                    .chatId(chatId)
                    .medias(makeMediaCollection(randomThreeCards))
                    .build();
            messageExecutorService.execute(cardImages);
        } catch (InterruptedException e) {
            log.error("Error sending message: " + e.getMessage());
        }
    }

    private List<Message> sendMessagesToThenDelete(Long chatId) {
        List<SendMessage> messages = makeSendMessagesList(chatId);
        List<Message> sentMessages = new ArrayList<>();

        for (SendMessage message : messages) {
            sentMessages.add(messageExecutorService.execute(message));
        }

        return sentMessages;
    }

    private List<SendMessage> makeSendMessagesList(Long chatId) {
        SendMessage crystalBall = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.CRYSTAL_BALL)
                .build();

        SendMessage afterCrystalBall = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.AFTER_CRYSTAL_BALL)
                .build();

        return List.of(crystalBall, afterCrystalBall);
    }

    private List<InputMediaPhoto> makeMediaCollection(HashMap<Integer, TarotCard> randomThreeCards) {
        List<InputMediaPhoto> imageList = new ArrayList<>();
        for (TarotCard card : randomThreeCards.values()) {
            imageList.add(InputMediaPhoto.builder()
                    .media(card.getImage(), card.name())
                    .build());
        }
        return imageList;
    }

    private String generateGeminiResponse(Message message, HashMap<Integer, TarotCard> randomThreeCards) {
        String mergedData = mergeUserAboutAndRequest(message);
        String response = geminiService.getResponse(randomThreeCards, mergedData);

        if(!response.equals(".Ошибка.")){
            saveResponse(message.getFrom().getId(), response);
        } else {
            saveError(message.getFrom().getId());
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

    private void saveResponse(@NonNull Long id, String response) {
        var user = userService.getByTelegramId(id);
        var request = requestService.findTop1ByUserOrderByTimestampDesc(user);
        request.setResponse(response);
        requestService.save(request);
    }

    private void saveError(@NonNull Long id) {
        var user = userService.getByTelegramId(id);
        var request = requestService.findTop1ByUserOrderByTimestampDesc(user);
        request.setResponse("error");
        requestService.save(request);
    }

    private void sendResponseToUser(Long chatId, String response) {
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
