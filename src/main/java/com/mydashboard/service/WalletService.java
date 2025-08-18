package com.mydashboard.service;

import com.mydashboard.entity.Wallet;

import java.util.List;

public interface WalletService {

    Wallet createWallet(Wallet wallet);

    Wallet updateWallet(Long id, Wallet wallet);

    Wallet getWalletById(Long id);

    List<Wallet> getAllWallets();

    void deleteWallet(Long id);

}

