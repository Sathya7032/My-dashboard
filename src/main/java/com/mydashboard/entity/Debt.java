package com.mydashboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "debts")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lenderName;

    private BigDecimal principalAmount;

    private double interestRate; // % per month

    private boolean paid = false;

    private LocalDateTime debtTakenAt;

    private BigDecimal paidAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "debt", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DebtInterest> interests;
}


