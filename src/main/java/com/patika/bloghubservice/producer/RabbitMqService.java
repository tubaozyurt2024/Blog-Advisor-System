package com.patika.bloghubservice.producer;

import com.patika.bloghubservice.config.RabbitMQConfig;
import com.patika.bloghubservice.dto.SendMEmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqService {
    // config dosyasından gelen template
    private final AmqpTemplate rabbitTemplate;

   // private final RabbitMQConfig rabbitMQConfig;
    public void senEmail(SendMEmailMessage sendMEmailMessage){
     //   rabbitTemplate.convertAndSend(rabbitMQConfig.getExchange(),rabbitMQConfig.getRoutingkey(),sendMEmailMessage);
       // log.info("Meesage kuyruğa gönderildi. kuyruk {}, message : {}" , rabbitMQConfig.getQueueName(),sendMEmailMessage);
    }
}
