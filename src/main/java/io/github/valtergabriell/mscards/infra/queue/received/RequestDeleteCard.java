package io.github.valtergabriell.mscards.infra.queue.received;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.valtergabriell.mscards.application.CardService;
import io.github.valtergabriell.mscards.application.domain.Card;
import io.github.valtergabriell.mscards.application.domain.dto.RequestCardData;
import io.github.valtergabriell.mscards.application.helpers.CreateRandomNumbersToCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestDeleteCard extends CreateRandomNumbersToCard {
    private final CardService cardService;

    @RabbitListener(queues = "delete-card-queue")
    public void receiveCardsRequest(@Payload String payload) {
        ObjectMapper om = new ObjectMapper();
        try {
            log.info("Recebendo dados: {}", payload);
            String value = om.readValue(payload, String.class);
            cardService.deleteCardByPersonIdentifier(value);
        } catch (JsonProcessingException e) {
            log.error("Falha ao receber dados: {}", payload);
        }
    }
}
