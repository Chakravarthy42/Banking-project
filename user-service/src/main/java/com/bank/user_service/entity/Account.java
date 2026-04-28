package com.bank.user_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Double balance;
}