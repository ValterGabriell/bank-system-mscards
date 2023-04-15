package io.github.valtergabriell.mscards.application.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BuyRequest {
    private BigDecimal buyValue;
    private String product;
    private int numberOfInstallments;

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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setBuyValue(BigDecimal buyValue) {
        this.buyValue = buyValue;
    }
}
