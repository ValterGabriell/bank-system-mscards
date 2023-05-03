package io.github.valtergabriell.mscards.application.domain.dto;

import java.math.BigDecimal;


public class RequestCardData {
    private BigDecimal cardLimit;
    private String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }
}
