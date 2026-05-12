package com.bank.user_service.service;

import com.bank.user_service.entity.Account;
import com.bank.user_service.entity.Transaction;
import com.bank.user_service.entity.User;
import com.bank.user_service.exception.CustomException;
import com.bank.user_service.repository.AccountRepository;
import com.bank.user_service.repository.TransactionRepository;
import com.bank.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EmailService emailService;

    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository
                .findByFromAccountIdOrToAccountId(accountId, accountId);
    }

    // ✅ DEPOSIT
    public String deposit(Long accountId, Double amount) {

        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        acc.setBalance(acc.getBalance() + amount);
        accountRepository.save(acc);

        Transaction tx = new Transaction();
        tx.setToAccountId(accountId);
        tx.setAmount(amount);
        tx.setType("DEPOSIT");
        tx.setTimestamp(LocalDateTime.now());

        transactionRepository.save(tx);

        // 📧 EMAIL
        emailService.sendEmail(acc.getEmail(), "Deposit", "₹" + amount + " credited");

        return "Deposit successful";
    }

    // ✅ WITHDRAW
    public String withdraw(Long accountId, Double amount) {

        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (acc.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        acc.setBalance(acc.getBalance() - amount);
        accountRepository.save(acc);

        Transaction tx = new Transaction();
        tx.setFromAccountId(accountId);
        tx.setAmount(amount);
        tx.setType("WITHDRAW");
        tx.setTimestamp(LocalDateTime.now());

        transactionRepository.save(tx);

        // 📧 EMAIL
        emailService.sendEmail(acc.getEmail(), "Withdraw", "₹" + amount + " debited");

        return "Withdraw successful";
    }

    // ✅ TRANSFER (MOST IMPORTANT)
    public String transfer(Long fromId, Long toId, Double amount) {

        Account sender = accountRepository.findById(fromId).orElseThrow();
        Account receiver = accountRepository.findById(toId).orElseThrow();

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction tx = new Transaction();
        tx.setFromAccountId(fromId);
        tx.setToAccountId(toId);
        tx.setAmount(amount);
        tx.setType("TRANSFER");
        tx.setTimestamp(LocalDateTime.now());

        transactionRepository.save(tx);

        // 📧 EMAILS
        emailService.sendEmail(sender.getEmail(),
                "Debit Alert", "₹" + amount + " sent");

        emailService.sendEmail(receiver.getEmail(),
                "Credit Alert", "₹" + amount + " received");

        return "Transfer successful";
    }

    // ✅ HISTORY
    public List<Transaction> history(Long accountId) {
        return transactionRepository
                .findByFromAccountIdOrToAccountId(accountId, accountId);
    }
}