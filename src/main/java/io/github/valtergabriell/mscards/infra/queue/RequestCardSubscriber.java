package io.github.valtergabriell.mscards.infra.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.valtergabriell.mscards.application.CardService;
import io.github.valtergabriell.mscards.application.domain.AccountCard;
import io.github.valtergabriell.mscards.application.domain.Card;
import io.github.valtergabriell.mscards.application.domain.dto.RequestCardData;
import io.github.valtergabriell.mscards.infra.repository.AccountCardRepository;
import io.github.valtergabriell.mscards.infra.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestCardSubscriber extends RandomValuesCreation implements RandomizeCardNumber, RandomizeSecurityCardNumber {
    private final CardService cardService;

    @RabbitListener(queues = "${mq.queues.create-card}")
    public void receiveCardsRequest(@Payload String payload) {

        try {
            var mapper = new ObjectMapper();
            RequestCardData cardData = mapper.readValue(payload, RequestCardData.class);
            log.info("ok" + cardData.getCpf().toString());


            //saving card
            Card card = cardService.saveCard(cardData);

            //saving card to client
            cardService.saveAccountCard(card, cardData);

            log.info(card.toString());

        } catch (Exception e) {
            log.error("Erro ao receber solicitação de emissão de cartão: {}", e.getMessage());
        }


    }


    @Override
    public String generateCardNumber(CardRepository cardRepository) {
        var finalNumber = createStringWithRandomNumbers(13);
        boolean cardNumberAlreadyExists = cardRepository.findByCardNumber(finalNumber).isPresent();

        if (cardNumberAlreadyExists) {
            generateCardNumber(cardRepository);
        }

        return finalNumber;
    }

    @Override
    public String generateSecurityNumber(CardRepository cardRepository) {
        var finalNumber = createStringWithRandomNumbers(3);
        boolean cardNumberAlreadyExists = cardRepository.findByCardNumber(finalNumber).isPresent();

        if (cardNumberAlreadyExists) {
            generateCardNumber(cardRepository);
        }

        return finalNumber;
    }
}
