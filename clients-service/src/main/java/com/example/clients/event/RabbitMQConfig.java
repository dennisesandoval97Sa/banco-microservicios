package com.example.clients.event;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Debe coincidir con el Accounts Service
    public static final String CLIENT_EXCHANGE = "client.exchange";
    public static final String CLIENT_ROUTING_KEY = "client.created";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CLIENT_EXCHANGE);
    }
}
