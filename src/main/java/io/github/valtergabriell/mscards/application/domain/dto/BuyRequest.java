package io.github.valtergabriell.mscards.application.domain.dto;

import java.math.BigDecimal;

public class BuyRequest {
    private BigDecimal productValue;
    private String product;
    private int numberOfInstallments;

    private String protocol;

    private AccountCardWithNotPassCard accountCard;

    private String cpf;

    public BuyRequest() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public BigDecimal getProductValue() {
        return productValue;
    }

    public void setProductValue(BigDecimal productValue) {
        this.productValue = productValue;
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

    public AccountCardWithNotPassCard getAccountCard() {
        return accountCard;
    }

    public void setAccountCard(AccountCardWithNotPassCard accountCard) {
        this.accountCard = accountCard;
    }

    public boolean isProductValueBiggerThanZero() {
        BigDecimal productValue = getProductValue();
        int i = productValue.intValue();
        return i > 0;
    }
}
