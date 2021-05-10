package com.backend.servicios;
import com.backend.dto.CustomMessage;
import com.backend.configuration.RabbitMQConfig;
import com.google.gson.Gson;
import com.rabbitmq.client.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class RabbitMQService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String queue = "lugares";
    private final String eventQueue = "eventQueue";
    private Gson gson;
    @Autowired
    private RabbitTemplate template;

    public RabbitMQService() {
        gson = new Gson();
    }


    public void pushMessage(CustomMessage cm) {
        this.template.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, cm);
    }

    public String pullMessage()
    {
        String bodyString = null;
        Channel queueChannel = template.execute(channel -> channel);
        try {
            if (queueChannel.messageCount(queue)>0) {
                GetResponse msgStructure = queueChannel.basicGet(queue, false);
                long idForAck = msgStructure.getEnvelope().getDeliveryTag();
                log.info("ID: "+idForAck);
                bodyString = new String(msgStructure.getBody());
                System.out.println("BODY: "+bodyString);

                boolean error = false;
                if (!error){
                    queueChannel.basicAck(idForAck, false);
                }else{
                    queueChannel.basicNack(idForAck, false, true);

                }

            }
        }catch (IOException ioException) {
            ioException.printStackTrace();
            log.error("FAIL");

        }
        return bodyString;
    }

    @RabbitListener(queues = eventQueue)
    private void process(Message message, Channel channel) {
        Long idForAck = message.getMessageProperties().getDeliveryTag();
        String messageStr = new String(message.getBody(), Charset.defaultCharset());
        log.info("message = [{}]", messageStr);
        try {

            boolean error = false;
            if (!error){
                channel.basicAck(idForAck, false);
            }else{
                channel.basicNack(idForAck,false,true);
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }






}
