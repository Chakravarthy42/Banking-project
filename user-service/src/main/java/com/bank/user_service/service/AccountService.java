package com.bank.user_service.service;


import com.bank.user_service.entity.Account;
import com.bank.user_service.entity.User;
import com.bank.user_service.repository.AccountRepository;
import com.bank.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public void createAccount(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Account account = new Account();
        account.setEmail(email);
        account.setUserId(user.getId());
        account.setBalance(0.0);

        accountRepository.save(account);
    }
    
    public double getBalance(Long userId) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return account.getBalance();
    }
}