package bots.telegram.tarobot.service;

import bots.telegram.tarobot.model.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardLayoutService {
    HashMap<Integer, Card> cards = new HashMap<>();
    Random random = new Random();

    public CardLayoutService(HashMap<Integer, Card> cards) {
        initCards();
    }

    private void initCards() {
        cards.put(1, Card.builder().name("Шут").image(new File("src/main/media/TarotCard1.jpg")).build());
        cards.put(2, Card.builder().name("Шут").image(new File("src/main/media/TarotCard2.jpg")).build());
        cards.put(3, Card.builder().name("Шут").image(new File("src/main/media/TarotCard3.jpg")).build());
        cards.put(4, Card.builder().name("Шут").image(new File("src/main/media/TarotCard4.jpg")).build());
        cards.put(5, Card.builder().name("Шут").image(new File("src/main/media/TarotCard5.jpg")).build());
        cards.put(6, Card.builder().name("Шут").image(new File("src/main/media/TarotCard6.jpg")).build());
        cards.put(7, Card.builder().name("Шут").image(new File("src/main/media/TarotCard7.jpg")).build());
        cards.put(8, Card.builder().name("Шут").image(new File("src/main/media/TarotCard8.jpg")).build());
        cards.put(9, Card.builder().name("Шут").image(new File("src/main/media/TarotCard9.jpg")).build());
        cards.put(10, Card.builder().name("Шут").image(new File("src/main/media/TarotCard10.jpg")).build());
        cards.put(11, Card.builder().name("Шут").image(new File("src/main/media/TarotCard11.jpg")).build());
        cards.put(12, Card.builder().name("Шут").image(new File("src/main/media/TarotCard12.jpg")).build());
        cards.put(13, Card.builder().name("Шут").image(new File("src/main/media/TarotCard13.jpg")).build());
        cards.put(14, Card.builder().name("Шут").image(new File("src/main/media/TarotCard14.jpg")).build());
        cards.put(15, Card.builder().name("Шут").image(new File("src/main/media/TarotCard15.jpg")).build());
        cards.put(16, Card.builder().name("Шут").image(new File("src/main/media/TarotCard16.jpg")).build());
        cards.put(17, Card.builder().name("Шут").image(new File("src/main/media/TarotCard17.jpg")).build());
        cards.put(18, Card.builder().name("Шут").image(new File("src/main/media/TarotCard18.jpg")).build());
        cards.put(19, Card.builder().name("Шут").image(new File("src/main/media/TarotCard19.jpg")).build());
        cards.put(20, Card.builder().name("Шут").image(new File("src/main/media/TarotCard20.jpg")).build());
        cards.put(21, Card.builder().name("Шут").image(new File("src/main/media/TarotCard21.jpg")).build());
        cards.put(22, Card.builder().name("Шут").image(new File("src/main/media/TarotCard22.jpg")).build());
        cards.put(23, Card.builder().name("Шут").image(new File("src/main/media/TarotCard23.jpg")).build());
        cards.put(24, Card.builder().name("Шут").image(new File("src/main/media/TarotCard24.jpg")).build());
        cards.put(25, Card.builder().name("Шут").image(new File("src/main/media/TarotCard25.jpg")).build());
        cards.put(26, Card.builder().name("Шут").image(new File("src/main/media/TarotCard26.jpg")).build());
        cards.put(27, Card.builder().name("Шут").image(new File("src/main/media/TarotCard27.jpg")).build());
        cards.put(28, Card.builder().name("Шут").image(new File("src/main/media/TarotCard28.jpg")).build());
        cards.put(29, Card.builder().name("Шут").image(new File("src/main/media/TarotCard29.jpg")).build());
        cards.put(30, Card.builder().name("Шут").image(new File("src/main/media/TarotCard30.jpg")).build());
        cards.put(31, Card.builder().name("Шут").image(new File("src/main/media/TarotCard31.jpg")).build());
        cards.put(32, Card.builder().name("Шут").image(new File("src/main/media/TarotCard32.jpg")).build());
        cards.put(33, Card.builder().name("Шут").image(new File("src/main/media/TarotCard33.jpg")).build());
        cards.put(34, Card.builder().name("Шут").image(new File("src/main/media/TarotCard34.jpg")).build());
        cards.put(35, Card.builder().name("Шут").image(new File("src/main/media/TarotCard35.jpg")).build());
        cards.put(36, Card.builder().name("Шут").image(new File("src/main/media/TarotCard36.jpg")).build());
        cards.put(37, Card.builder().name("Шут").image(new File("src/main/media/TarotCard37.jpg")).build());
        cards.put(38, Card.builder().name("Шут").image(new File("src/main/media/TarotCard38.jpg")).build());
        cards.put(39, Card.builder().name("Шут").image(new File("src/main/media/TarotCard39.jpg")).build());
        cards.put(40, Card.builder().name("Шут").image(new File("src/main/media/TarotCard40.jpg")).build());
        cards.put(41, Card.builder().name("Шут").image(new File("src/main/media/TarotCard41.jpg")).build());
        cards.put(42, Card.builder().name("Шут").image(new File("src/main/media/TarotCard42.jpg")).build());
        cards.put(43, Card.builder().name("Шут").image(new File("src/main/media/TarotCard43.jpg")).build());
        cards.put(44, Card.builder().name("Шут").image(new File("src/main/media/TarotCard44.jpg")).build());
        cards.put(45, Card.builder().name("Шут").image(new File("src/main/media/TarotCard45.jpg")).build());
        cards.put(46, Card.builder().name("Шут").image(new File("src/main/media/TarotCard46.jpg")).build());
        cards.put(47, Card.builder().name("Шут").image(new File("src/main/media/TarotCard47.jpg")).build());
        cards.put(48, Card.builder().name("Шут").image(new File("src/main/media/TarotCard48.jpg")).build());
        cards.put(49, Card.builder().name("Шут").image(new File("src/main/media/TarotCard49.jpg")).build());
        cards.put(50, Card.builder().name("Шут").image(new File("src/main/media/TarotCard50.jpg")).build());
        cards.put(51, Card.builder().name("Шут").image(new File("src/main/media/TarotCard51.jpg")).build());
        cards.put(52, Card.builder().name("Шут").image(new File("src/main/media/TarotCard52.jpg")).build());
        cards.put(53, Card.builder().name("Шут").image(new File("src/main/media/TarotCard53.jpg")).build());
        cards.put(54, Card.builder().name("Шут").image(new File("src/main/media/TarotCard54.jpg")).build());
        cards.put(55, Card.builder().name("Шут").image(new File("src/main/media/TarotCard55.jpg")).build());
        cards.put(56, Card.builder().name("Шут").image(new File("src/main/media/TarotCard56.jpg")).build());
        cards.put(57, Card.builder().name("Шут").image(new File("src/main/media/TarotCard57.jpg")).build());
        cards.put(58, Card.builder().name("Шут").image(new File("src/main/media/TarotCard58.jpg")).build());
        cards.put(59, Card.builder().name("Шут").image(new File("src/main/media/TarotCard59.jpg")).build());
        cards.put(60, Card.builder().name("Шут").image(new File("src/main/media/TarotCard60.jpg")).build());
        cards.put(61, Card.builder().name("Шут").image(new File("src/main/media/TarotCard61.jpg")).build());
        cards.put(62, Card.builder().name("Шут").image(new File("src/main/media/TarotCard62.jpg")).build());
        cards.put(63, Card.builder().name("Шут").image(new File("src/main/media/TarotCard63.jpg")).build());
        cards.put(64, Card.builder().name("Шут").image(new File("src/main/media/TarotCard64.jpg")).build());
        cards.put(65, Card.builder().name("Шут").image(new File("src/main/media/TarotCard65.jpg")).build());
        cards.put(66, Card.builder().name("Шут").image(new File("src/main/media/TarotCard66.jpg")).build());
        cards.put(67, Card.builder().name("Шут").image(new File("src/main/media/TarotCard67.jpg")).build());
        cards.put(68, Card.builder().name("Шут").image(new File("src/main/media/TarotCard68.jpg")).build());
        cards.put(69, Card.builder().name("Шут").image(new File("src/main/media/TarotCard69.jpg")).build());
        cards.put(70, Card.builder().name("Шут").image(new File("src/main/media/TarotCard70.jpg")).build());
        cards.put(71, Card.builder().name("Шут").image(new File("src/main/media/TarotCard71.jpg")).build());
        cards.put(72, Card.builder().name("Шут").image(new File("src/main/media/TarotCard72.jpg")).build());
        cards.put(73, Card.builder().name("Шут").image(new File("src/main/media/TarotCard73.jpg")).build());
        cards.put(74, Card.builder().name("Шут").image(new File("src/main/media/TarotCard74.jpg")).build());
        cards.put(75, Card.builder().name("Шут").image(new File("src/main/media/TarotCard75.jpg")).build());
        cards.put(76, Card.builder().name("Шут").image(new File("src/main/media/TarotCard76.jpg")).build());
        cards.put(77, Card.builder().name("Шут").image(new File("src/main/media/TarotCard77.jpg")).build());
        cards.put(78, Card.builder().name("Шут").image(new File("src/main/media/TarotCard78.jpg")).build());
    }

    public void beginLayout(Message message) {
        randomizeThreeCards();
        transferDataToGemini();
        getGeminiResponse();
        sendResponseToUser();
    }

    private void randomizeThreeCards() {
        random.nextInt();
        // TODO cards randomize
    }
}
