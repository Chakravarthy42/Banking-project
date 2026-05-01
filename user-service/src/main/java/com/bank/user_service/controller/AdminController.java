package com.bank.user_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String admin() {
        return "Admin access granted";
    }
}
