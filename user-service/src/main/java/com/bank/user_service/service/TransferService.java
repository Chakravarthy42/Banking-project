package com.bank.user_service.service;

import com.bank.user_service.entity.Account;
import com.bank.user_service.entity.Transaction;
import com.bank.user_service.repository.AccountRepository;
import com.bank.user_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EmailService emailService;

    public String transfer(Long fromId, Long toId, Double amount) {

        System.out.println("=== TRANSFER START ===");

        Account sender = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Account receiver = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        // ✅ SAVE TRANSACTION (FIXED)
        Transaction tx = new Transaction();
        tx.setFromAccountId(fromId);
        tx.setToAccountId(toId);
        tx.setAmount(amount);
        tx.setType("TRANSFER");
        tx.setTimestamp(LocalDateTime.now());

        transactionRepository.save(tx);

        // ✅ EMAIL
        try {
            emailService.sendEmail(
                    "sender@gmail.com",
                    "Money Sent",
                    "₹" + amount + " sent"
            );

            emailService.sendEmail(
                    "receiver@gmail.com",
                    "Money Received",
                    "₹" + amount + " received"
            );
        } catch (Exception e) {
            System.out.println("Email failed");
        }

        return "Transfer successful";
    }
}