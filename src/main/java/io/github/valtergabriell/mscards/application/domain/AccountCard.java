package io.github.valtergabriell.mscards.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.valtergabriell.mscards.application.domain.dto.AccountCardWithNotPassCard;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "account_card")
public class AccountCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idClientCard;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(nullable = false)
    private BigDecimal cardLimit;

    @Column(name = "current_limit", nullable = false)
    private BigDecimal currentLimit;

    @JsonIgnore
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public AccountCardWithNotPassCard toDto() {
        return new AccountCardWithNotPassCard(
                this.idClientCard, this.identifier, this.cardLimit, this.currentLimit
        );
    }

    public Long getIdClientCard() {
        return idClientCard;
    }

    public void setIdClientCard(Long idClientCard) {
        this.idClientCard = idClientCard;
    }

    public BigDecimal getCurrentLimit() {
        return currentLimit;
    }

    public void setCurrentLimit(BigDecimal currentLimit) {
        this.currentLimit = currentLimit;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(BigDecimal cardLimit) {
        this.cardLimit = cardLimit;
    }

}
