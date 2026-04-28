package com.bank.user_service.service;


import com.bank.user_service.entity.Account;
import com.bank.user_service.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Long userId) {
        Account acc = new Account();
        acc.setUserId(userId);
        acc.setBalance(0.0);
        return accountRepository.save(acc);
    }
}