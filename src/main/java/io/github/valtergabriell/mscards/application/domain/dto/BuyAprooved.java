package io.github.valtergabriell.mscards.application.domain.dto;

import java.math.BigDecimal;
import java.net.URI;

public class BuyAprooved {
    private String message;
    private BigDecimal newLimite;
    private URI headerLocation;

    public BuyAprooved(String message, BigDecimal newLimite, URI headerLocation) {
        this.message = message;
        this.newLimite = newLimite;
        this.headerLocation = headerLocation;
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

    public URI getHeaderLocation() {
        return headerLocation;
    }

    public void setHeaderLocation(URI headerLocation) {
        this.headerLocation = headerLocation;
    }

    public BuyAprooved() {
    }
}
