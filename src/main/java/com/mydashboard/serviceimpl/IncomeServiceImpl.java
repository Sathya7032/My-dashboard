package com.mydashboard.serviceimpl;

import com.mydashboard.entity.Income;
import com.mydashboard.entity.Wallet;
import com.mydashboard.enums.PaymentType;
import com.mydashboard.exceptions.InvalidWalletOperationException;
import com.mydashboard.repo.IncomeRepository;
import com.mydashboard.repo.WalletRepository;
import com.mydashboard.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final WalletRepository walletRepository;

    @Override
    public Income addIncome(Income income) {
        // Attach wallet (since only 1 wallet exists in system)
        Wallet wallet = walletRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new InvalidWalletOperationException("No wallet exists. Please create a wallet first."));

        income.setWallet(wallet);
        return incomeRepository.save(income);
    }

    @Override
    public Income updateIncome(Long id, Income income) {
        // Attach wallet (since only 1 wallet exists in system)
        Wallet wallet = walletRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new InvalidWalletOperationException("No wallet exists. Please create a wallet first."));

        Income existing = incomeRepository.findById(id)
                .orElseThrow(() -> new InvalidWalletOperationException("Income not found with id " + id));

        // Before updating, adjust wallet (remove old amount from previous type)
        if (existing.getPaymentType() == PaymentType.CASH) {
            wallet.setCashInHand(wallet.getCashInHand().subtract(existing.getAmount()));
        } else if (existing.getPaymentType() == PaymentType.ONLINE) {
            wallet.setCashInAccount(wallet.getCashInAccount().subtract(existing.getAmount()));
        }

        // Update income details
        existing.setAmount(income.getAmount());
        existing.setSource(income.getSource());
        existing.setPaymentType(income.getPaymentType());
        existing.setReceivedDate(income.getReceivedDate());

        // After updating, adjust wallet (add new amount to new type)
        if (income.getPaymentType() == PaymentType.CASH) {
            wallet.setCashInHand(wallet.getCashInHand().add(income.getAmount()));
            wallet.setCurrentBalance(wallet.getCashInAccount().add(wallet.getCashInHand()));
        } else if (income.getPaymentType() == PaymentType.ONLINE) {
            wallet.setCashInAccount(wallet.getCashInAccount().add(income.getAmount()));
            wallet.setCurrentBalance(wallet.getCashInAccount().add(wallet.getCashInHand()));
        }



        // Save both wallet & income
        walletRepository.save(wallet);
        return incomeRepository.save(existing);
    }

    @Override
    public Income getIncomeById(Long id) {
        return incomeRepository.findById(id)
                .orElseThrow(() -> new InvalidWalletOperationException("Income not found with id " + id));
    }

    @Override
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    @Override
    public void deleteIncome(Long id) {
        Income existing = incomeRepository.findById(id)
                .orElseThrow(() -> new InvalidWalletOperationException("Income not found with id " + id));
        incomeRepository.delete(existing);
    }
}

