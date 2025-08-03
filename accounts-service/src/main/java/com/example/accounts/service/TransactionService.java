package com.example.accounts.service;

import com.example.accounts.dto.AccountStatementDTO;
import com.example.accounts.entity.Account;
import com.example.accounts.entity.Transaction;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;

    public Transaction registerTransaction(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal newBalance = account.getInitialBalance();

        if ("withdraw".equalsIgnoreCase(transaction.getTransactionType())) {
            if (account.getInitialBalance().compareTo(transaction.getValue()) < 0) {
                throw new RuntimeException("Insufficient balance");
            }
            newBalance = account.getInitialBalance().subtract(transaction.getValue());
        } else if ("deposit".equalsIgnoreCase(transaction.getTransactionType())) {
            newBalance = account.getInitialBalance().add(transaction.getValue());
        } else {
            throw new RuntimeException("Invalid transaction type");
        }

        account.setInitialBalance(newBalance);
        transaction.setBalance(newBalance);
        transaction.setTransactionDate(LocalDateTime.now());

        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getReport(LocalDateTime start, LocalDateTime end, Long accountId) {
        return transactionRepository.findByTransactionDateBetweenAndAccountId(start, end, accountId);
    }

    public AccountStatementDTO getAccountStatement(LocalDateTime start, LocalDateTime end, Long clientId) {
        Map<String, Object> clientData = restTemplate.getForObject(
                "http://clients-service:8080/clients/" + clientId,
                Map.class
        );

        String clientName = clientData != null ? (String) clientData.getOrDefault("name", "Unknown") : "Unknown";
        String clientIdentification = clientData != null ? (String) clientData.getOrDefault("identification", "Unknown") : "Unknown";

        List<Account> accounts = accountRepository.findByClientId(clientId);

        List<AccountStatementDTO.AccountInfo> accountInfos = accounts.stream().map(acc -> {
            List<Transaction> txs = transactionRepository.findByTransactionDateBetweenAndAccountId(start, end, acc.getId());
            List<AccountStatementDTO.TransactionInfo> txInfos = txs.stream()
                    .map(t -> new AccountStatementDTO.TransactionInfo(
                            t.getTransactionDate(),
                            t.getTransactionType(),
                            t.getValue(),
                            t.getBalance()
                    ))
                    .collect(Collectors.toList());
            return new AccountStatementDTO.AccountInfo(
                    acc.getAccountNumber(),
                    acc.getAccountType(),
                    acc.getInitialBalance(),
                    txInfos
            );
        }).collect(Collectors.toList());

        return new AccountStatementDTO(clientName, clientIdentification, accountInfos);
    }
}
