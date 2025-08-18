package com.mydashboard.service;

import com.mydashboard.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    Transaction updateTransaction(Long id, Transaction transaction);

    void deleteTransaction(Long id);

    Transaction getTransaction(Long id);

    List<Transaction> getAllTransactions();
}

