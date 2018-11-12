package com.nobbyknox.absa.queue;

import com.nobbyknox.absa.AbsaApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

@Component
public class PaymentReceiver {
    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void receiveMessage(String message) {
        System.out.println("[PaymentReceiver] Received: " + message);
        latch.countDown();

        rabbitTemplate.convertAndSend(AbsaApplication.topicExchangeName, Queues.STATUS.toString(), "Please check status");
    }

    public void receiveMessage(byte[] message) {
        receiveMessage(new String(message, StandardCharsets.UTF_8));
    }

    CountDownLatch getLatch() {
        return latch;
    }
}
