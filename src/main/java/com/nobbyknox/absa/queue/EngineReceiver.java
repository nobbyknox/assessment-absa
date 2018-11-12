package com.nobbyknox.absa.queue;

import com.nobbyknox.absa.AbsaApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class EngineReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void receiveMessage(String message) {
        System.out.println("[EngineReceiver] Received: " + message);

        rabbitTemplate.convertAndSend(AbsaApplication.topicExchangeName, Queues.ACK.toString(), "ACK");
    }

    public void receiveMessage(byte[] message) {
        receiveMessage(new String(message, StandardCharsets.UTF_8));
    }
}
