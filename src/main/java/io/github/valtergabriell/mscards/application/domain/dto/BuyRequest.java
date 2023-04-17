package io.github.valtergabriell.mscards.application.domain.dto;

import io.github.valtergabriell.mscards.application.domain.AccountCard;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BuyRequest {
    private BigDecimal buyValue;
    private String product;
    private int numberOfInstallments;

    private String protocol;

    private AccountCardDTO accountCard;

    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BuyRequest() {
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public BigDecimal getBuyValue() {
        return buyValue;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setBuyValue(BigDecimal buyValue) {
        this.buyValue = buyValue;
    }

    public AccountCardDTO getAccountCard() {
        return accountCard;
    }

    public void setAccountCard(AccountCardDTO accountCard) {
        this.accountCard = accountCard;
    }
}
