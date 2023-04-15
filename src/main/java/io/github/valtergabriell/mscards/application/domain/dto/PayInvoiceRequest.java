package io.github.valtergabriell.mscards.application.domain.dto;

import java.math.BigDecimal;

public class PayInvoiceRequest {
    private BigDecimal paymentValue;
    private int numberOfInstallment;

    public PayInvoiceRequest() {
    }

    public BigDecimal getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(BigDecimal paymentValue) {
        this.paymentValue = paymentValue;
    }

    public int getNumberOfInstallment() {
        return numberOfInstallment;
    }

    public void setNumberOfInstallment(int numberOfInstallment) {
        this.numberOfInstallment = numberOfInstallment;
    }
}
