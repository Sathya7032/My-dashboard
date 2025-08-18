package com.mydashboard.service;

import com.mydashboard.entity.Income;

import java.util.List;

public interface IncomeService {

    Income addIncome(Income income);

    Income updateIncome(Long id, Income income);

    Income getIncomeById(Long id);

    List<Income> getAllIncomes();

    void deleteIncome(Long id);
}

