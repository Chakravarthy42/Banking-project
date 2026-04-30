package com.bank.user_service.service;

import com.bank.user_service.entity.Account;
import com.bank.user_service.entity.Transaction;
import com.bank.user_service.exception.CustomException;
import com.bank.user_service.repository.AccountRepository;
import com.bank.user_service.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void transfer(Long fromId, Long toId, Double amount) {

        Account from = accountRepository.findById(fromId).orElseThrow();
        Account to = accountRepository.findById(toId).orElseThrow();

        if (from.getBalance() < amount) {
            throw new CustomException("Insufficient balance");
        }

        // deduct
        from.setBalance(from.getBalance() - amount);

        // add
        to.setBalance(to.getBalance() + amount);

        accountRepository.save(from);
        accountRepository.save(to);

        // record transaction
        Transaction tx = new Transaction();
        tx.setAccountId(fromId);
        tx.setAmount(amount);
        tx.setType("TRANSFER");

        transactionRepository.save(tx);
    }
}