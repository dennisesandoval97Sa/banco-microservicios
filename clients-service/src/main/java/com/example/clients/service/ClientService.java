package com.example.clients.service;

import com.example.clients.entity.Client;
import com.example.clients.event.RabbitMQConfig;
import com.example.clients.exception.ResourceNotFoundException;
import com.example.clients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final RabbitTemplate rabbitTemplate;

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    public Client create(Client client) {
        Client saved = clientRepository.save(client);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.CLIENT_EXCHANGE,
                RabbitMQConfig.CLIENT_ROUTING_KEY,
                saved.getId()
        );

        System.out.println("📤 Evento enviado a RabbitMQ: Cliente creado con ID " + saved.getId());

        return saved;
    }

    public Client update(Long id, Client client) {
        Client existing = getById(id);
        client.setId(existing.getId());
        return clientRepository.save(client);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}
