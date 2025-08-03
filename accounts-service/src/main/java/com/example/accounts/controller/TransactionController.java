package com.example.accounts.controller;

import com.example.accounts.dto.AccountStatementDTO;
import com.example.accounts.entity.Transaction;
import com.example.accounts.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.registerTransaction(transaction);
    }

    @GetMapping("/report")
    public List<Transaction> getReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam Long accountId) {
        return transactionService.getReport(start, end, accountId);
    }

    @GetMapping("/statement")
    public AccountStatementDTO getStatement(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam Long clientId) {
        return transactionService.getAccountStatement(start, end, clientId);
    }
}
