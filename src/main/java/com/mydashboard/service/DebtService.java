package com.mydashboard.service;

import com.mydashboard.entity.Debt;
import com.mydashboard.entity.DebtInterest;

import java.math.BigDecimal;
import java.util.List;

public interface DebtService {

    Debt createDebt(Debt debt);

    Debt updateDebt(Long id, Debt debt);

    void deleteDebt(Long id);

    List<Debt> getAllDebts();

    Debt getDebtById(Long id);

    BigDecimal getTotalOutstanding();

    void generateMonthlyInterest();

    DebtInterest payInterest(Long interestId);
}


