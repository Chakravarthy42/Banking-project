package com.bank.user_service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bank.user_service.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public void deposit(@RequestParam Long accountId, @RequestParam Double amount) {
        transactionService.deposit(accountId, amount);
    }

    @PostMapping("/withdraw")
    public void withdraw(@RequestParam Long accountId, @RequestParam Double amount) {
        transactionService.withdraw(accountId, amount);
    }
}