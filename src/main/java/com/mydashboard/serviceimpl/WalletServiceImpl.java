package com.mydashboard.service.impl;

import com.mydashboard.entity.Wallet;

import com.mydashboard.exceptions.InvalidWalletOperationException;
import com.mydashboard.repo.WalletRepository;
import com.mydashboard.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    private void validateWallet(Wallet wallet) {
        if (wallet.getCashInHand() != null && wallet.getCashInHand().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidWalletOperationException("Cash in hand cannot be less than 0");
        }
        if (wallet.getCashInAccount() != null && wallet.getCashInAccount().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidWalletOperationException("Cash in account cannot be less than 0");
        }
    }

    private void recalculateBalance(Wallet wallet) {
        BigDecimal cashInHand = wallet.getCashInHand() != null ? wallet.getCashInHand() : BigDecimal.ZERO;
        BigDecimal cashInAccount = wallet.getCashInAccount() != null ? wallet.getCashInAccount() : BigDecimal.ZERO;

        BigDecimal total = cashInHand.add(cashInAccount);

        if (total.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidWalletOperationException("Balance cannot be less than 0");
        }

        wallet.setCurrentBalance(total);
    }

    @Override
    public Wallet createWallet(Wallet wallet) {
        validateWallet(wallet);
        recalculateBalance(wallet);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet updateWallet(Long id, Wallet wallet) {
        Wallet existing = walletRepository.findById(id)
                .orElseThrow(() -> new InvalidWalletOperationException("Wallet not found with id " + id));

        existing.setCashInHand(wallet.getCashInHand());
        existing.setCashInAccount(wallet.getCashInAccount());

        validateWallet(existing);
        recalculateBalance(existing);

        return walletRepository.save(existing);
    }

    @Override
    public Wallet getWalletById(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new InvalidWalletOperationException("Wallet not found with id " + id));
    }

    @Override
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    @Override
    public void deleteWallet(Long id) {
        Wallet existing = walletRepository.findById(id)
                .orElseThrow(() -> new InvalidWalletOperationException("Wallet not found with id " + id));
        walletRepository.delete(existing);
    }
}
