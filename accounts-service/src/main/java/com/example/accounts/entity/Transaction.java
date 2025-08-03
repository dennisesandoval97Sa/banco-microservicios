package com.example.accounts.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions") // ✅ coincide con init.sql
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "transaction_type")
    private String transactionType;

    private BigDecimal value;

    private BigDecimal balance;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
}
