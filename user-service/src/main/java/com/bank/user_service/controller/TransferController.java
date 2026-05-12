package com.bank.user_service.controller;

import com.bank.user_service.dto.TransferRequest;
import com.bank.user_service.service.TransferService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping
    public String transfer(@RequestParam Long fromId,
                           @RequestParam Long toId,
                           @RequestParam Double amount) {
        return transferService.transfer(fromId, toId, amount);

    }
}