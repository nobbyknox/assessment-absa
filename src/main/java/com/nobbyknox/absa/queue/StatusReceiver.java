package com.nobbyknox.absa.queue;

import com.nobbyknox.absa.AbsaApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class StatusReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void receiveMessage(String message) {
        System.out.println("[StatusReceiver] Received: " + message);

        rabbitTemplate.convertAndSend(AbsaApplication.topicExchangeName, Queues.ENGINE.toString(), "Engine thing");
    }

    public void receiveMessage(byte[] message) {
        receiveMessage(new String(message, StandardCharsets.UTF_8));
    }
}
