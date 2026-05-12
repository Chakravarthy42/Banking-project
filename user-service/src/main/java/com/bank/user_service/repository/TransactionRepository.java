package com.bank.user_service.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.user_service.entity.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccountIdOrToAccountId(Long from, Long to);
}