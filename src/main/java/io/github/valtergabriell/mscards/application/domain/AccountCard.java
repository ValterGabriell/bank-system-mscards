package io.github.valtergabriell.mscards.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

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

    @OneToMany(targetEntity = ProductsBuyed.class, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductsBuyed> productsBuyeds;

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

    public List<ProductsBuyed> getProductsBuyeds() {
        return productsBuyeds;
    }

    public void setProductsBuyeds(List<ProductsBuyed> productsBuyeds) {
        this.productsBuyeds = productsBuyeds;
    }
}
