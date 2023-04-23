package io.github.valtergabriell.mscards.application.domain.dto;

import java.math.BigDecimal;

public class AccountCardWithNotPassCard {
    private Long idClientCard;

    private String cpf;

    private BigDecimal cardLimit;

    private BigDecimal currentLimit;

    public AccountCardWithNotPassCard(Long idClientCard, String cpf, BigDecimal cardLimit, BigDecimal currentLimit) {
        this.idClientCard = idClientCard;
        this.cpf = cpf;
        this.cardLimit = cardLimit;
        this.currentLimit = currentLimit;
    }

    public Long getIdClientCard() {
        return idClientCard;
    }

    public String getCpf() {
        return cpf;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public BigDecimal getCurrentLimit() {
        return currentLimit;
    }
}
