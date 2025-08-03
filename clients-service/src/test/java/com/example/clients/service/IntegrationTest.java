package com.example.clients.service;

import com.example.clients.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testListClientsEndpoint() {
        Client client = new Client();
        client.setName("Nuevo Cliente");
        client.setGender("Male");
        client.setAge(30);
        client.setIdentification("1234567890");
        client.setAddress("Calle Falsa 123");
        client.setPhone("0999999999");
        client.setPassword("pass");
        client.setStatus(true);

        restTemplate.postForEntity("/clients", client, Client.class);

        ResponseEntity<String> response = restTemplate.getForEntity("/clients", String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).contains("Nuevo Cliente");
    }
}
