package com.mydashboard.serviceimpl;

import com.mydashboard.entity.Debt;
import com.mydashboard.entity.DebtInterest;
import com.mydashboard.repo.DebtInterestRepository;
import com.mydashboard.repo.DebtRepository;
import com.mydashboard.service.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;
    private final DebtInterestRepository interestRepository;

    @Override
    public Debt createDebt(Debt debt) {
        return debtRepository.save(debt);
    }

    @Override
    public Debt updateDebt(Long id, Debt debt) {
        Debt existing = debtRepository.findById(id).orElseThrow(() -> new RuntimeException("Debt not found"));
        existing.setLenderName(debt.getLenderName());
        existing.setPrincipalAmount(debt.getPrincipalAmount());
        existing.setInterestRate(debt.getInterestRate());
        existing.setPaidAmount(debt.getPaidAmount());
        return debtRepository.save(existing);
    }

    @Override
    public void deleteDebt(Long id) {
        debtRepository.deleteById(id);
    }

    @Override
    public List<Debt> getAllDebts() {
        return debtRepository.findAll();
    }

    @Override
    public Debt getDebtById(Long id) {
        return debtRepository.findById(id).orElseThrow(() -> new RuntimeException("Debt not found"));
    }

    @Override
    public BigDecimal getTotalOutstanding() {
        return debtRepository.findAll().stream()
                .map(Debt::getPrincipalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void generateMonthlyInterest() {
        List<Debt> debts = debtRepository.findAll();

        for (Debt debt : debts) {
            long months = ChronoUnit.MONTHS.between(
                    debt.getDebtTakenAt().toLocalDate(),
                    LocalDate.now()
            );

            for (int i = 1; i <= months; i++) {
                LocalDate monthDate = debt.getDebtTakenAt().toLocalDate().plusMonths(i);

                boolean alreadyGenerated = debt.getInterests().stream()
                        .anyMatch(interest -> interest.getInterestDate().equals(monthDate));

                if (!alreadyGenerated) {
                    // Simple Interest formula: (P * R / 100) * (1/12) each month
                    BigDecimal monthlyInterest = debt.getPrincipalAmount()
                            .multiply(BigDecimal.valueOf(debt.getInterestRate()))
                            .divide(BigDecimal.valueOf(100 * 12), 2, RoundingMode.HALF_UP);

                    DebtInterest interest = DebtInterest.builder()
                            .interestDate(monthDate)
                            .interestAmount(monthlyInterest)
                            .paid(false)
                            .debt(debt)
                            .build();

                    interestRepository.save(interest);
                }
            }
        }
    }


    @Override
    public DebtInterest payInterest(Long interestId) {
        DebtInterest interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new RuntimeException("Interest not found"));
        interest.setPaid(true);
        return interestRepository.save(interest);
    }
}


