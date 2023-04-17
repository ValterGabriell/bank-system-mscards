package io.github.valtergabriell.mscards.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.valtergabriell.mscards.application.domain.dto.AccountCardDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "account_card")
public class AccountCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idClientCard;

    @Column(name = "cpf", nullable = false)
    private String cpf;

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

    public AccountCardDTO toDto() {
        return new AccountCardDTO(
                this.idClientCard, this.cpf, this.cardLimit, this.currentLimit
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
