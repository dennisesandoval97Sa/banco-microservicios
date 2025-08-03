package com.example.accounts.event;

import com.example.accounts.entity.Account;
import com.example.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ClientCreatedListener {

    private final AccountRepository accountRepository;

    @RabbitListener(queues = RabbitMQConfig.CLIENT_CREATED_QUEUE)
    public void handleClientCreated(Long clientId) {
        Account account = new Account();
        account.setClientId(clientId);
        account.setAccountNumber("ACC-" + System.currentTimeMillis());
        account.setAccountType("savings");
        account.setInitialBalance(BigDecimal.ZERO);
        account.setStatus(true);
        accountRepository.save(account);

        System.out.println("📩 Cuenta creada automáticamente para cliente ID: " + clientId);
    }
}
