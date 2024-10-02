package bots.telegram.tarobot.service;

import bots.telegram.tarobot.util.TarotCardsUtil;
import bots.telegram.tarobot.util.enums.BotMessage;
import bots.telegram.tarobot.util.enums.TarotCard;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
public class CardLayoutService {
    private final UserService userService;
    private final RequestService requestService;
    private final MessageExecutorService messageExecutorService;
    private final GeminiService geminiService;

    public void beginLayout(Message message) {
        HashMap<Integer, TarotCard> randomThreeCards = TarotCardsUtil.getRandomThreeCards();
        sendPreparingMessages(message.getChatId(), randomThreeCards);
        sendResponseToUser(message.getChatId(), getGeminiResponse(message, randomThreeCards));
    }

    @Async
    protected void sendPreparingMessages(Long chatId, HashMap<Integer, TarotCard> randomThreeCards) {
        sendCardImages(chatId, randomThreeCards);
        sendAutoDeleteMessages(chatId);
        sendBeforeResultMessage(chatId);
    }

    private void sendCardImages(Long chatId, HashMap<Integer, TarotCard> randomThreeCards) {
        SendMediaGroup cardImages = SendMediaGroup.builder()
                .chatId(chatId)
                .medias(makeMediaCollection(randomThreeCards))
                .build();
        messageExecutorService.execute(cardImages);
    }

    private void sendAutoDeleteMessages(Long chatId) {
        List<SendMessage> messages = makeSendMessagesList(chatId);
        List<Message> sentMessages = new ArrayList<>();

        for (SendMessage message : messages) {
            sentMessages.add(messageExecutorService.execute(message));
        }

        deleteMessagesAfterDelay(chatId, sentMessages);
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

    private void deleteMessagesAfterDelay(Long chatId, List<Message> sentMessages) {
        try {
            TimeUnit.SECONDS.sleep(3);

            for (Message sentMessage : sentMessages) {
                DeleteMessage deleteMessage = DeleteMessage.builder()
                        .chatId(chatId)
                        .messageId(sentMessage.getMessageId())
                        .build();
                messageExecutorService.execute(deleteMessage);
            }
        } catch (InterruptedException e) {
            log.error("Error sending message: " + e.getMessage());
        }
    }

    private void sendBeforeResultMessage(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.BEFORE_RESPONSE)
                .build();
        messageExecutorService.execute(message);
    }

    private Collection<? extends InputMedia> makeMediaCollection(HashMap<Integer, TarotCard> randomThreeCards) {
        List<InputMediaPhoto> imageList = new ArrayList<>();
        for (TarotCard card : randomThreeCards.values()) {
            imageList.add(InputMediaPhoto.builder()
                    .media(card.getImage(), card.name())
                    .build());
        }
        return imageList;
    }

    private String getGeminiResponse(Message message, HashMap<Integer, TarotCard> randomThreeCards) {
        String mergedData = mergeUserAboutAndRequest(message);
        String response = geminiService.getResponse(randomThreeCards, mergedData);
        saveResponse(message.getFrom().getId(), response);
        return response;
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
