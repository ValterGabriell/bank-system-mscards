package io.github.valtergabriell.mscards.infra.repository;

import io.github.valtergabriell.mscards.application.domain.AccountCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface AccountCardRepository extends JpaRepository<AccountCard, Long> {


    AccountCard findByCpf(String cpf);


}
