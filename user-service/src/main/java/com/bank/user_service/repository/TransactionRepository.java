package com.bank.user_service.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.user_service.entity.Transaction;



public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}