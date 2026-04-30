package com.bank.user_service.controller;


import com.bank.user_service.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bank.user_service.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/history")
    public List<Transaction> getHistory(@RequestParam Long accountId) {
        return transactionService.getTransactions(accountId);
    }

    @PostMapping("/deposit")
    public void deposit(@RequestParam Long accountId, @RequestParam Double amount) {
        transactionService.deposit(accountId, amount);
    }

    @PostMapping("/withdraw")
    public void withdraw(@RequestParam Long accountId, @RequestParam Double amount) {
        transactionService.withdraw(accountId, amount);
    }
}