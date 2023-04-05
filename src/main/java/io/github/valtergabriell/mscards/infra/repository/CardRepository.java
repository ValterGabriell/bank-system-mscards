package io.github.valtergabriell.mscards.infra.repository;


import io.github.valtergabriell.mscards.application.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, String> {
    Optional<Card> findByCardNumber(String expectedNumberOfCard);
}
