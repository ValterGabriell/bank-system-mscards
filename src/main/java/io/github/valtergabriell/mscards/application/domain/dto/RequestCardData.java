package io.github.valtergabriell.mscards.application.domain.dto;

import io.github.valtergabriell.mscards.excpetions.RequestException;

import java.math.BigDecimal;

import static io.github.valtergabriell.mscards.excpetions.ExceptionsValues.CPF_LENGHT_INVALID;


public class RequestCardData {
    private BigDecimal cardLimit;
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public boolean isCpfLenghtEqual11() {
        boolean isCpfLenghtOk = getCpf().length() == 11;
        if (!isCpfLenghtOk) {
            throw new RequestException(CPF_LENGHT_INVALID);
        }
        return true;
    }

}
