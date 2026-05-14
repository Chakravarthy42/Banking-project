package com.bank.user_service.service;

import com.bank.user_service.entity.Account;
import com.bank.user_service.entity.Transaction;
import com.bank.user_service.repository.AccountRepository;
import com.bank.user_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // ✅ Get logged-in user email
    private String getLoggedInEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    // ✅ Transaction history
    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository
                .findByFromAccountIdOrToAccountId(accountId, accountId);
    }

    // ✅ DEPOSIT
    public String deposit(Long accountId, Double amount) {

        String email = getLoggedInEmail();

        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!acc.getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized access ❌");
        }

        acc.setBalance(acc.getBalance() + amount);
        accountRepository.save(acc);

        Transaction tx = new Transaction();
        tx.setToAccountId(accountId);
        tx.setAmount(amount);
        tx.setType("DEPOSIT");
        tx.setTimestamp(LocalDateTime.now());
        transactionRepository.save(tx);

        // 📧 EMAIL
        emailService.sendEmail(
                acc.getEmail(),
                "💰 Deposit Successful - Chakri Bank",
                "Dear Customer,\n\n" +
                        "We are pleased to inform you that a deposit of ₹" + amount + " has been successfully credited to your account.\n\n" +
                        "Account ID: " + accountId + "\n" +
                        "Updated Balance: ₹" + acc.getBalance() + "\n" +
                        "Date & Time: " + LocalDateTime.now() + "\n\n" +
                        "If you did not perform this transaction, please contact support immediately.\n\n" +
                        "Thank you for banking with Chakri Bank.\n\n" +
                        "Regards,\nChakri Bank"
        );

        return "Deposit successful";
    }

    // ✅ WITHDRAW
    public String withdraw(Long accountId, Double amount) {

        String email = getLoggedInEmail();

        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!acc.getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized access ❌");
        }

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
        emailService.sendEmail(
                acc.getEmail(),
                "💸 Withdrawal Alert - Chakri Bank",
                "Dear Customer,\n\n" +
                        "A withdrawal of ₹" + amount + " has been processed from your account.\n\n" +
                        "Account ID: " + accountId + "\n" +
                        "Remaining Balance: ₹" + acc.getBalance() + "\n" +
                        "Date & Time: " + LocalDateTime.now() + "\n\n" +
                        "If this transaction was not authorized by you, please report immediately.\n\n" +
                        "Thank you for banking with Chakri Bank.\n\n" +
                        "Regards,\nChakri Bank"
        );

        return "Withdraw successful";
    }

    // ✅ TRANSFER
    public String transfer(Long fromId, Long toId, Double amount) {

        String email = getLoggedInEmail();

        Account sender = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account receiver = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (!sender.getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized transfer ❌");
        }

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

        // 📧 SENDER EMAIL
        emailService.sendEmail(
                sender.getEmail(),
                "🔻 Debit Alert - Money Sent",
                "Dear Customer,\n\n" +
                        "You have successfully transferred ₹" + amount + " to another account.\n\n" +
                        "From Account ID: " + fromId + "\n" +
                        "To Account ID: " + toId + "\n" +
                        "Remaining Balance: ₹" + sender.getBalance() + "\n" +
                        "Date & Time: " + LocalDateTime.now() + "\n\n" +
                        "If you did not initiate this transaction, please contact us immediately.\n\n" +
                        "Regards,\nChakri Bank"
        );

        // 📧 RECEIVER EMAIL
        emailService.sendEmail(
                receiver.getEmail(),
                "🔺 Credit Alert - Money Received",
                "Dear Customer,\n\n" +
                        "You have received ₹" + amount + " in your account.\n\n" +
                        "From Account ID: " + fromId + "\n" +
                        "To Account ID: " + toId + "\n" +
                        "Updated Balance: ₹" + receiver.getBalance() + "\n" +
                        "Date & Time: " + LocalDateTime.now() + "\n\n" +
                        "Thank you for banking with Chakri Bank.\n\n" +
                        "Regards,\nChakri Bank"
        );

        return "Transfer successful";
    }

    // ✅ HISTORY
    public List<Transaction> history(Long accountId) {
        return transactionRepository
                .findByFromAccountIdOrToAccountId(accountId, accountId);
    }
}