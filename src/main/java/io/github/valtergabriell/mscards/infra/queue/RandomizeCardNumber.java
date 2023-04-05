package io.github.valtergabriell.mscards.infra.queue;

import io.github.valtergabriell.mscards.infra.repository.CardRepository;

public interface RandomizeCardNumber {
    public String generateCardNumber(CardRepository cardRepository);
}
