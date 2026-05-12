package com.bank.user_service.controller;



import com.bank.user_service.entity.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bank.user_service.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public String deposit(@RequestParam Long accountId,
                          @RequestParam Double amount) {
        return transactionService.deposit(accountId, amount);
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long accountId,
                           @RequestParam Double amount) {
        return transactionService.withdraw(accountId, amount);
    }

    @GetMapping("/history")
    public List<Transaction> history(@RequestParam Long accountId) {
        return transactionService.getTransactions(accountId);
    }
}