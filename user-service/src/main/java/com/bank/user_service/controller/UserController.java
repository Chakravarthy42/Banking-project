package com.bank.user_service.controller;

import com.bank.user_service.entity.User;
import com.bank.user_service.service.TransferService;
import com.bank.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransferService transferService;

    // ✅ REGISTER
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.login(user.getEmail(), user.getPassword());
    }

    // ✅ TEST
    @GetMapping("/test")
    public String test() {
        return "Access Granted";
    }

}