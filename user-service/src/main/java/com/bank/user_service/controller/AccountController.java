package com.bank.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.user_service.entity.User;
import com.bank.user_service.repository.UserRepository;
import com.bank.user_service.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        double balance = accountService.getBalance(user.getId());

        return ResponseEntity.ok(balance);
    }
}