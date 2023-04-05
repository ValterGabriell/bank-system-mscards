package io.github.valtergabriell.mscards.application.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Card {
    @Id
    @Column(nullable = false)
    private String cardId;
    @Column(nullable = false)
    private BigDecimal cardLimit;
    @Column(nullable = false)
    private String cardSecurityNumber;
    @Column(nullable = false)
    private String cardNumber;
    @Column(nullable = false)
    private LocalDate expireDate;





}
