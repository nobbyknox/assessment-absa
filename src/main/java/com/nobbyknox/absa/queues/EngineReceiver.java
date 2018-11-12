package com.nobbyknox.absa.queues;

import com.nobbyknox.absa.services.EngineService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EngineReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EngineService service;

    public void receiveMessage(byte[] message) {
        try {
            service.validateMessage(message);
            service.advanceFlow(message);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
