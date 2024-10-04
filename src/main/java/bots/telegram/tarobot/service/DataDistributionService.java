package bots.telegram.tarobot.service;

import bots.telegram.tarobot.util.enums.BotMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.invoices.CreateInvoiceLink;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DataDistributionService {
    private final UserService userService;
    private final RequestService requestService;
    private final MessageExecutorService messageExecutorService;
    private final PaymentService paymentService;
    private final KeyboardService keyboardService;
    private final CardLayoutService cardLayoutService;

    public void distribute(Message message) {
        var user = userService.findByTelegramId(message.getFrom().getId());
        if (user != null) {
            if (user.getAbout() == null) {
                user.setAbout(message.getText());
                userService.save(user);
                sendSuccessRegistrationMessage(message.getFrom().getId());
            } else {
                var lastRequest = requestService.findTop1ByUserOrderByTimestampDesc(user);
                if (lastRequest != null && lastRequest.getResponse() != null && lastRequest.getResponse().equals("error")) {
                    lastRequest.setRequest(message.getText());
                    requestService.save(lastRequest);
                    cardLayoutService.beginLayout(message);
                } else if (lastRequest != null && lastRequest.getRequest() == null) {
                    lastRequest.setRequest(message.getText());
                    requestService.save(lastRequest);
                    askUserToPay(message.getChatId());
                } else {
                    sendWrongRequestMessage(message.getChatId());
                }
            }
        } else {
            sendWrongRequestMessage(message.getChatId());
        }
    }

    private void sendSuccessRegistrationMessage(Long chatId) {
        SendMessage message = SendMessage.builder()
                .text(BotMessage.SUCCESS_REGISTRATION)
                .chatId(chatId)
                .replyMarkup(keyboardService.getReplyKeyboardMarkup())
                .build();
        messageExecutorService.execute(message);
    }

    private void askUserToPay(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.ASK_PAYMENT)
                .replyMarkup(keyboardService.getInlineKeyboardMarkup(makePaymentUrl()))
                .build();
        messageExecutorService.execute(message);
    }

    private String makePaymentUrl() {
        CreateInvoiceLink invoiceLink =  paymentService.makePaymentInvoiceLink("Расклад на картах Таро", "В услугу входит проведение онлайн-расклада на картах Таро. Осуществляя оплату Вы подтверждаете, что понимаете,что данная услуга носит развлекательный характер.", BigDecimal.valueOf(99), "tarotLayout");
        return messageExecutorService.execute(invoiceLink);
    }

    private void sendWrongRequestMessage(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(BotMessage.WRONG_COMMAND)
                .build();
        messageExecutorService.execute(message);
    }
}
