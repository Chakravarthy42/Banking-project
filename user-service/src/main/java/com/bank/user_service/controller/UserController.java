package com.bank.user_service.controller;


import com.bank.user_service.dto.LoginRequest;
import com.bank.user_service.dto.TransferRequest;
import com.bank.user_service.entity.Account;
import com.bank.user_service.entity.User;
import com.bank.user_service.service.AccountService;
import com.bank.user_service.service.TransferService;
import com.bank.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferService transferService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);

    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return userService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/create")
    public Account create(@RequestParam Long userId) {
        return accountService.createAccount(userId);
    }

    @PostMapping
    public void transfer(@Valid @RequestBody TransferRequest request) {
        transferService.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount()
        );
    }
}