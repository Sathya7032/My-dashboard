package com.mydashboard.serviceimpl;

import com.mydashboard.entity.Transaction;
import com.mydashboard.entity.Wallet;
import com.mydashboard.enums.PaymentType;
import com.mydashboard.exceptions.InvalidWalletOperationException;
import com.mydashboard.repo.TransactionRepository;
import com.mydashboard.repo.WalletRepository;
import com.mydashboard.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        Wallet wallet = walletRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new InvalidWalletOperationException("Wallet not found"));
        if (wallet.getCurrentBalance().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidWalletOperationException("Current balance is 0. Cannot perform transaction.");
        }

        if (transaction.getPaymentType() == PaymentType.CASH) {
            if (wallet.getCashInHand().compareTo(transaction.getAmount()) < 0) {
                throw new InvalidWalletOperationException("Insufficient cash in hand for this transaction.");
            }
            wallet.setCashInHand(wallet.getCashInHand().subtract(transaction.getAmount()));
            wallet.setCurrentBalance(wallet.getCashInAccount().add(wallet.getCashInHand()));
        } else if (transaction.getPaymentType() == PaymentType.ONLINE) {
            if (wallet.getCashInAccount().compareTo(transaction.getAmount()) < 0) {
                throw new InvalidWalletOperationException("Insufficient cash in account for this transaction.");
            }
            wallet.setCashInAccount(wallet.getCashInAccount().subtract(transaction.getAmount()));
            wallet.setCurrentBalance(wallet.getCashInAccount().add(wallet.getCashInHand()));
        }

        transaction.setWallet(wallet);

        walletRepository.save(wallet);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction transaction) {
        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new InvalidWalletOperationException("Transaction not found with id " + id));

        Wallet wallet = walletRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new InvalidWalletOperationException("Wallet not found"));


        existing.setAmount(transaction.getAmount());
        existing.setPaidTo(transaction.getPaidTo());
        existing.setPaymentType(transaction.getPaymentType());
        if (transaction.getPaymentType() == PaymentType.CASH) {
            if (wallet.getCashInHand().compareTo(transaction.getAmount()) < 0) {
                throw new InvalidWalletOperationException("Insufficient cash in hand for this transaction.");
            }
            wallet.setCashInAccount(wallet.getCashInAccount().add(transaction.getAmount()));
            wallet.setCashInHand(wallet.getCashInHand().subtract(transaction.getAmount()));
            wallet.setCurrentBalance(wallet.getCashInAccount().add(wallet.getCashInHand()));
        } else if (transaction.getPaymentType() == PaymentType.ONLINE) {
            if (wallet.getCashInAccount().compareTo(transaction.getAmount()) < 0) {
                throw new InvalidWalletOperationException("Insufficient cash in account for this transaction.");
            }

            wallet.setCashInHand(wallet.getCashInHand().add(transaction.getAmount()));
            wallet.setCashInAccount(wallet.getCashInAccount().subtract(transaction.getAmount()));
            wallet.setCurrentBalance(wallet.getCashInAccount().add(wallet.getCashInHand()));
        }

        transaction.setWallet(wallet);

        walletRepository.save(wallet);
        return transactionRepository.save(existing);
    }

    @Override
    public void deleteTransaction(Long id) {
        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new InvalidWalletOperationException("Transaction not found with id " + id));

        Wallet wallet = existing.getWallet();

        walletRepository.save(wallet);
        transactionRepository.delete(existing);
    }

    @Override
    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new InvalidWalletOperationException("Transaction not found with id " + id));
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

}

