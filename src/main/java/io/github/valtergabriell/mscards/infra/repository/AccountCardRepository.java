package io.github.valtergabriell.mscards.infra.repository;

import io.github.valtergabriell.mscards.application.domain.AccountCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountCardRepository extends JpaRepository<AccountCard, Long> {
}
