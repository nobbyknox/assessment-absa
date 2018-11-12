package com.nobbyknox.absa.queues;

import com.nobbyknox.absa.services.PaymentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PaymentService service;

    public void receiveMessage(byte[] message) {
        try {
            service.validateMessage(message);
            service.progressFlow(message);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
