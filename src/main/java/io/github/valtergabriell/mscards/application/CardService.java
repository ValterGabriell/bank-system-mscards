package io.github.valtergabriell.mscards.application;


import io.github.valtergabriell.mscards.application.domain.AccountCard;
import io.github.valtergabriell.mscards.application.domain.Card;
import io.github.valtergabriell.mscards.application.domain.dto.RequestCardData;
import io.github.valtergabriell.mscards.infra.queue.RandomValuesCreation;
import io.github.valtergabriell.mscards.infra.queue.RandomizeCardNumber;
import io.github.valtergabriell.mscards.infra.queue.RandomizeSecurityCardNumber;
import io.github.valtergabriell.mscards.infra.repository.AccountCardRepository;
import io.github.valtergabriell.mscards.infra.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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
        return card;
    }

    public void saveAccountCard(Card card, RequestCardData cardData) {
        AccountCard accountCard = new AccountCard();
        accountCard.setCardLimit(card.getCardLimit());
        accountCard.setCpf(cardData.getCpf());
        accountCard.setCard(card);
        accountCardRepository.save(accountCard);
    }

    public AccountCard getAccountCardByClientCpf(String cpf) {
        return accountCardRepository.findByCpf(cpf);
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
}
