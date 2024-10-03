package bots.telegram.tarobot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.invoices.CreateInvoiceLink;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private @Value("${provider.token}") String providerToken;

    public CreateInvoiceLink makePaymentInvoiceLink(String productName, String description, BigDecimal price, String payload) {
        return CreateInvoiceLink.builder()
                .title(productName)
                .description(description)
                .payload(payload)
                .providerToken(providerToken)
                .currency("RUB")
                .price(new LabeledPrice("Цена", price.multiply(BigDecimal.valueOf(100L)).intValue()))
                .build();
    }
}
