package com.example.accounts.service;

import com.example.accounts.entity.Account;
import com.example.accounts.entity.Transaction;
import com.example.accounts.exception.InsufficientBalanceException;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {
        Account account = new Account();
        account.setId(1L);
        account.setInitialBalance(BigDecimal.valueOf(100));

        Transaction tx = new Transaction();
        tx.setAccountId(1L);
        tx.setTransactionType("withdraw");
        tx.setValue(BigDecimal.valueOf(200));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(InsufficientBalanceException.class, () -> transactionService.registerTransaction(tx));
    }
}
