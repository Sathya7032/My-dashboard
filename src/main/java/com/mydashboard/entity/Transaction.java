package com.mydashboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mydashboard.enums.PaymentType;
import com.mydashboard.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String paidTo;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;


    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "balance_id")
    private Wallet wallet;

}

