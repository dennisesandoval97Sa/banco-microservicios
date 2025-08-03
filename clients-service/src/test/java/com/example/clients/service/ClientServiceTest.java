package com.example.clients.service;

import com.example.clients.entity.Client;
import com.example.clients.exception.ResourceNotFoundException;
import com.example.clients.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @Mock
    RabbitTemplate rabbitTemplate;

    @InjectMocks
    ClientService clientService;

    public ClientServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById_Found() {
        Client client = new Client();
        client.setId(1L);
        client.setName("John");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client result = clientService.getById(1L);

        assertEquals("John", result.getName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(clientRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.getById(2L));
        verify(clientRepository, times(1)).findById(2L);
    }
}
