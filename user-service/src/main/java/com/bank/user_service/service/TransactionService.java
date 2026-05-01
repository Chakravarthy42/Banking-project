package com.bank.user_service.service;




import com.bank.user_service.entity.Account;
import com.bank.user_service.entity.Transaction;
import com.bank.user_service.exception.CustomException;
import com.bank.user_service.repository.AccountRepository;
import com.bank.user_service.repository.TransactionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public void deposit(Long accountId, Double amount) {
        Account acc = accountRepository.findById(accountId).orElseThrow();

        acc.setBalance(acc.getBalance() + amount);
        accountRepository.save(acc);

        Transaction tx = new Transaction();
        tx.setAccountId(accountId);
        tx.setAmount(amount);
        tx.setType("DEPOSIT");

        transactionRepository.save(tx);
    }

    public void withdraw(Long accountId, Double amount) {
        Account acc = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));;

        if (acc.getBalance() < amount) {
            throw new CustomException("Insufficient balance");
        }

        acc.setBalance(acc.getBalance() - amount);
        accountRepository.save(acc);

        Transaction tx = new Transaction();
        tx.setAccountId(accountId);
        tx.setAmount(amount);
        tx.setType("WITHDRAW");

        transactionRepository.save(tx);
    }
   
}
