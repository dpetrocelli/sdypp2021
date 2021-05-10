package com.backend.configuration;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    
    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    public static final String EXCHANGE_NAME = "e1-exchange";
    public static final String ROUTING_KEY = "e1-lugares";

    private static final String QUEUE_NAME = "lugares";
    private static final boolean IS_DURABLE_QUEUE = true;

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, IS_DURABLE_QUEUE);
    }

    @Bean
    Queue eventQueue() {
        return new Queue("eventQueue", IS_DURABLE_QUEUE);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);

    }
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);

        return cachingConnectionFactory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    // Versión autoACK
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.containerAckMode(AcknowledgeMode.MANUAL);

        //rabbitTemplate.consusetMaxConcurrentConsumers(10);
        return rabbitTemplate;
    }


    /*
    Versión con manual ACK


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) { 
    	RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // Message sending failure returned to the queue, publisher returns needs to be configured for the profile: true
        rabbitTemplate.setMandatory(true);
 
        // Message return, the configuration file needs to configure publisher returns: true
        // The ReturnCallback interface is used to implement the callback when a message is sent to the RabbitMQ switch, but there is no corresponding queue bound to the switch.
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("Message sending failed:No corresponding queue bound to switch");
        });
 
        // Message confirmation, the configuration file needs to configure publisher confirms: true
        // The ConfirmCallback interface is used to receive ack callbacks after messages are sent to the RabbitMQ exchanger.
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("Message sent successfully:Message sent to RabbitMQ exchanger");
            } else {
                System.out.println("Message sending failed:Message not sent to RabbitMQ exchanger");
            }
        });
 
        return rabbitTemplate;
    }
        */

}
