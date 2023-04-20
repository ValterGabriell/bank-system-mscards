package io.github.valtergabriell.mscards.infra.queue.received;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.valtergabriell.mscards.application.CardService;
import io.github.valtergabriell.mscards.application.RandomValuesCreation;
import io.github.valtergabriell.mscards.application.domain.Card;
import io.github.valtergabriell.mscards.application.domain.dto.RequestCardData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestCardSubscriber extends RandomValuesCreation {
    private final CardService cardService;

    @RabbitListener(queues = "create-card-queue")
    public void receiveCardsRequest(@Payload String payload) {

        try {
            var mapper = new ObjectMapper();
            RequestCardData cardData = mapper.readValue(payload, RequestCardData.class);
            //saving card
            Card card = cardService.saveCard(cardData);
            //saving card to client
            cardService.saveAccoundCard(card, cardData);
        } catch (Exception e) {
            log.error("Erro ao receber solicitação de emissão de cartão: {}", e.getMessage());
        }


    }
}
