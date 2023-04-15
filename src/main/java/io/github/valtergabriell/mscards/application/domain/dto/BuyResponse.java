package io.github.valtergabriell.mscards.application.domain.dto;

import java.math.BigDecimal;
import java.net.URI;

public class BuyResponse {
    private String message;
    private BigDecimal newLimite;
    private BigDecimal installmentValue;
    private int numberOfInstallment;
    private String product;

    public BuyResponse() {
    }

    public BuyResponse(String message, BigDecimal newLimite, BigDecimal installmentValue, int numberOfInstallment, String product) {
        this.message = message;
        this.newLimite = newLimite;
        this.installmentValue = installmentValue;
        this.numberOfInstallment = numberOfInstallment;
        this.product = product;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getNewLimite() {
        return newLimite;
    }

    public void setNewLimite(BigDecimal newLimite) {
        this.newLimite = newLimite;
    }

    public BigDecimal getInstallmentValue() {
        return installmentValue;
    }

    public void setInstallmentValue(BigDecimal installmentValue) {
        this.installmentValue = installmentValue;
    }

    public int getNumberOfInstallment() {
        return numberOfInstallment;
    }

    public void setNumberOfInstallment(int numberOfInstallment) {
        this.numberOfInstallment = numberOfInstallment;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
