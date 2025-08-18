package com.mydashboard.entity;

import com.mydashboard.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String source; // e.g. Salary, Business, Investment

    private LocalDateTime receivedDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Wallet wallet;

    @PrePersist
    public void updateBalance() {
        if (wallet != null && amount != null) {
            switch (paymentType) {
                case CASH -> {
                    wallet.setCashInHand(wallet.getCashInHand().add(amount));
                    wallet.updateBalance(amount); // updates currentBalance
                }
                case ONLINE -> {
                    wallet.setCashInAccount(wallet.getCashInAccount().add(amount));
                    wallet.updateBalance(amount); // updates currentBalance
                }
                default -> wallet.updateBalance(amount);
            }
        }
    }
}

