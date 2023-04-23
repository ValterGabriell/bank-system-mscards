package io.github.valtergabriell.mscards.application.domain.dto;

import java.math.BigDecimal;


public class RequestCardData {
    private BigDecimal cardLimit;
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }
}
