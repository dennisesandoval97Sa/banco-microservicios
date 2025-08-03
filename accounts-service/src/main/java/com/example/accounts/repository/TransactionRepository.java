package com.example.accounts.repository;

import com.example.accounts.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTransactionDateBetweenAndAccountId(LocalDateTime start, LocalDateTime end, Long accountId);
}
