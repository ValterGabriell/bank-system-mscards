package io.github.valtergabriell.mscards.infra.queue;

import io.github.valtergabriell.mscards.infra.repository.CardRepository;

public interface RandomizeSecurityCardNumber {
    public String generateSecurityNumber(CardRepository cardRepository);
}
