package com.desafioSmileGo.infrastructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${queue.payment-status}")
    private String paymentQueue;

    @Bean
    public Queue queue() {
        return new Queue(paymentQueue, true);
    }
}