package com.votacao.domain.service;

import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VotacaoProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    @SneakyThrows
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(routingkey, message);
    }


}
