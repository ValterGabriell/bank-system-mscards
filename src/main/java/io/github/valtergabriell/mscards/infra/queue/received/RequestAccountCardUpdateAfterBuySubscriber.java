package io.github.valtergabriell.mscards.infra.queue.received;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.valtergabriell.mscards.application.CardService;
import io.github.valtergabriell.mscards.application.domain.AccountCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestAccountCardUpdateAfterBuySubscriber extends RandomValuesCreation {
    private final CardService cardService;

    @RabbitListener(queues = "update-account-card")
    public void receiveCardsRequest(@Payload String payload) {
        log.info("limite atualizado recebido: " + payload);
        try {
            var mapper = new ObjectMapper();
            AccountCard accountCard = mapper.readValue(payload, AccountCard.class);
            //saving account card
            cardService.updateCurrentLimitAfterBuy(accountCard);
        } catch (Exception e) {
            log.error("Erro ao receber solicitação de emissão de cartão: {}", e.getMessage());
        }


    }
}
