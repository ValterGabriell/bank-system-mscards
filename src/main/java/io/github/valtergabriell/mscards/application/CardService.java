package io.github.valtergabriell.mscards.application;


import io.github.valtergabriell.mscards.application.domain.AccountCard;
import io.github.valtergabriell.mscards.application.domain.Card;
import io.github.valtergabriell.mscards.application.domain.dto.BuyRequest;
import io.github.valtergabriell.mscards.application.domain.dto.CommonResponse;
import io.github.valtergabriell.mscards.application.domain.dto.RequestCardData;
import io.github.valtergabriell.mscards.infra.queue.RandomValuesCreation;
import io.github.valtergabriell.mscards.infra.queue.RandomizeCardNumber;
import io.github.valtergabriell.mscards.infra.queue.RandomizeSecurityCardNumber;
import io.github.valtergabriell.mscards.infra.repository.AccountCardRepository;
import io.github.valtergabriell.mscards.infra.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardService extends RandomValuesCreation implements RandomizeCardNumber, RandomizeSecurityCardNumber {
    private final CardRepository cardRepository;
    private final AccountCardRepository accountCardRepository;

    public Card saveCard(RequestCardData cardData) {
        Card card = new Card();
        card.setCardId(UUID.randomUUID().toString());
        card.setCardNumber(generateCardNumber(cardRepository));
        card.setCardLimit(cardData.getCardLimit());
        card.setCardSecurityNumber(generateSecurityNumber(cardRepository));
        card.setExpireDate(LocalDate.now().plusYears(2));
        cardRepository.save(card);
        log.info("card salvo " + card);
        return card;
    }

    public void saveAccountCard(Card card, RequestCardData cardData) {
        AccountCard accountCard = new AccountCard();
        accountCard.setCardLimit(card.getCardLimit());
        accountCard.setCpf(cardData.getCpf());
        accountCard.setCard(card);
        accountCard.setCurrentLimit(card.getCardLimit());
        log.info("account card salvo " + accountCard);
        accountCardRepository.save(accountCard);
    }

    public CommonResponse<AccountCard> getAccountCardByClientCpf(String cpf) {
        AccountCard accountCard = accountCardRepository.findByCpf(cpf);
        CommonResponse<AccountCard> commonResponse = new CommonResponse<>();
        if (accountCard != null) {
            commonResponse.setData(accountCard);
            commonResponse.setMessage("Tudo certo!");
        } else {
            commonResponse.setMessage("Falha ao receber o cartao do cleinte");
        }
        return commonResponse;
    }

    public List<AccountCard> getAllAccountCard() {
        return accountCardRepository.findAll();
    }

    @Override
    public String generateCardNumber(CardRepository cardRepository) {
        return generateRandomValue(cardRepository, 13);
    }

    @Override
    public String generateSecurityNumber(CardRepository cardRepository) {
        return generateRandomValue(cardRepository, 3);
    }

    public void buySomething(String cpf, BuyRequest buyRequest) {
        AccountCard accountCard = accountCardRepository.findByCpf(cpf);
        if (accountCard != null) {
            BigDecimal currentAccountCardLimit = accountCard.getCurrentLimit();
            if (currentAccountCardLimit.intValue() > buyRequest.getBuyValue().intValue()) {
                BigDecimal newCurrentLimit = currentAccountCardLimit.subtract(buyRequest.getBuyValue());
                log.info("compra efetuada no valor " + buyRequest.getBuyValue().intValue());
                accountCard.setCurrentLimit(newCurrentLimit);
                log.info("novo limite setado de " + accountCard.getCurrentLimit());
                accountCardRepository.save(accountCard);
            } else {
                log.error("compra negada por falta de limite");
            }
        }
    }
}
