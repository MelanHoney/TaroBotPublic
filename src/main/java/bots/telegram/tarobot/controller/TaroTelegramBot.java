package bots.telegram.tarobot.controller;

import bots.telegram.tarobot.commands.TaroBotCommand;
import bots.telegram.tarobot.factory.BotCommandFactory;
import bots.telegram.tarobot.service.CardLayoutService;
import bots.telegram.tarobot.service.DataDistributionService;
import bots.telegram.tarobot.service.MessageExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class TaroTelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private final BotCommandFactory botCommandFactory;
    private final DataDistributionService dataDistributionService;
    private final MessageExecutorService messageExecutorService;
    private final CardLayoutService cardLayoutService;

    private @Value("${telegram.bot.token}") String botToken;

    public TaroTelegramBot(BotCommandFactory botCommandFactory,
                           @Value("${telegram.bot.token}") String botToken,
                           DataDistributionService dataDistributionService, MessageExecutorService messageExecutorService, CardLayoutService cardLayoutService) {
        this.botCommandFactory = botCommandFactory;
        this.dataDistributionService = dataDistributionService;
        this.messageExecutorService = messageExecutorService;
        this.cardLayoutService = cardLayoutService;
    }


    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            processUpdate(update);
        } else if (update.hasPreCheckoutQuery()) {
            if (update.getPreCheckoutQuery().getInvoicePayload() != null) {
                messageExecutorService.execute(new AnswerPreCheckoutQuery(update.getPreCheckoutQuery().getId(), true));
            }
        } else if (update.hasMessage() && update.getMessage().hasSuccessfulPayment()) {
            cardLayoutService.beginLayout(update.getMessage());
        }
    }

    private void processUpdate(Update update) {
        TaroBotCommand command = botCommandFactory.getBotCommand(update.getMessage().getText(), update.getMessage());
        if (command != null) {
            command.process(update.getMessage());
        } else {
            dataDistributionService.distribute(update.getMessage());
        }
    }
}
