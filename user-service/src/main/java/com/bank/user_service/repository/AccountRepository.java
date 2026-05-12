package com.bank.user_service.repository;

import com.bank.user_service.entity.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
   Optional <Account> findByUserId(Long userId);

}
