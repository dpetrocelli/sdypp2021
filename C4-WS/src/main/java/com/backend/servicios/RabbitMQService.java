package com.backend.servicios;
import com.backend.dto.CustomMessage;
import com.backend.configuration.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class RabbitMQService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    public void send(CustomMessage cm) {
        this.template.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, cm);

        System.out.println(" [x] Sent '" + cm.getText() + "'");
    }



    public CustomMessage pullMessage()
    {
        log.info("receiving the message ");
        CustomMessage message = (CustomMessage) this.template.receiveAndConvert("lugares");

        if (message != null) {

            log.info("  received message [" + message.getText().toString() + "] ");
        }
        return message;
    }
}
