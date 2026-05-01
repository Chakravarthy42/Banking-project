package com.bank.user_service.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.user_service.entity.Transaction;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);


}