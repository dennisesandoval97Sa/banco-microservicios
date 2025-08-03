package com.example.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatementDTO {
    private String clientName;
    private String clientIdentification;
    private List<AccountInfo> accounts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountInfo {
        private String accountNumber;
        private String accountType;
        private BigDecimal currentBalance;
        private List<TransactionInfo> transactions;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransactionInfo {
        private LocalDateTime date;
        private String type;
        private BigDecimal value;
        private BigDecimal balance;
    }
}
