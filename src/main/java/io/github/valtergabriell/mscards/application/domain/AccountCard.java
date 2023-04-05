package io.github.valtergabriell.mscards.application.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class AccountCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idClientCard;

    @Column(nullable = false)
    private String accountCpf;

    @ManyToOne
    @JoinColumn(name = "cardId")
    private Card card;

    @Column(nullable = false)
    private BigDecimal cardLimit;


}
