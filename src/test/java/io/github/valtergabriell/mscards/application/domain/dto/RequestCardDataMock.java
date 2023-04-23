package io.github.valtergabriell.mscards.application.domain.dto;

import io.github.valtergabriell.mscards.excpetions.RequestException;

import java.math.BigDecimal;

import static io.github.valtergabriell.mscards.excpetions.ExceptionsValues.CPF_LENGHT_INVALID;


public class RequestCardDataMock {
    private BigDecimal cardLimit;
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(BigDecimal cardLimit) {
        this.cardLimit = cardLimit;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void isCpfLenghtEqual11() {
        boolean isCpfLenghtOk = getCpf().length() == 11;
        if (!isCpfLenghtOk) {
            throw new RequestException(CPF_LENGHT_INVALID);
        }
    }

    public boolean isCpfContainsOnlyNumbers(){
        String regex = "^[0-9]+$";
        return getCpf().matches(regex);
    }



}
