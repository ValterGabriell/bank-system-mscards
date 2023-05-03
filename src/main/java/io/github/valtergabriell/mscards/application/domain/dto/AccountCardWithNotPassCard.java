package io.github.valtergabriell.mscards.application.domain.dto;

import java.math.BigDecimal;

public class AccountCardWithNotPassCard {
    private Long idClientCard;

    private String identifier;

    private BigDecimal cardLimit;

    private BigDecimal currentLimit;

    public AccountCardWithNotPassCard(Long idClientCard, String identifier, BigDecimal cardLimit, BigDecimal currentLimit) {
        this.idClientCard = idClientCard;
        this.identifier = identifier;
        this.cardLimit = cardLimit;
        this.currentLimit = currentLimit;
    }

    public Long getIdClientCard() {
        return idClientCard;
    }

    public String getIdentifier() {
        return identifier;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public BigDecimal getCurrentLimit() {
        return currentLimit;
    }
}
