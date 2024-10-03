package bots.telegram.tarobot.service;

import bots.telegram.tarobot.util.enums.BotMessage;
import bots.telegram.tarobot.util.enums.TarotCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
public class CardLayoutPreparingService {
    private final MessageExecutorService messageExecutorService;

    @Async("taskExecutor")
    public CompletableFuture<List<Message>> sendPrepareMessages(Long chatId, HashMap<Integer, TarotCard> randomThreeCards) {
        try {
            System.out.println(Thread.currentThread());
            sendBeforeCardsMessage(chatId);
            sendCardImagesWithDelay(chatId, randomThreeCards);
            List<Message> messagesToThenDelete = sendMessagesToThenDelete(chatId);

            TimeUnit.SECONDS.sleep(3);

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

}
