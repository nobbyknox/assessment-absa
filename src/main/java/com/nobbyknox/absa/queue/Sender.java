package com.nobbyknox.absa.queue;

import com.nobbyknox.absa.AbsaApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Sender implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final PaymentReceiver paymentReceiver;

    public Sender(PaymentReceiver paymentReceiver, RabbitTemplate rabbitTemplate) {
        this.paymentReceiver = paymentReceiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(AbsaApplication.topicExchangeName, Queues.PAYMENT.toString(), "Handle payment");
        paymentReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
