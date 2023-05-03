package io.github.valtergabriell.mscards.application.domain.dto;

import java.math.BigDecimal;

public class BuyRequest {
    private BigDecimal productValue;
    private String product;
    private int numberOfInstallments;

    private String protocol;

    private AccountCardWithNotPassCard accountCard;

    private String identifier;

    public BuyRequest() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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


}
