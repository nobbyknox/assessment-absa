package com.nobbyknox.absa.services;

import com.nobbyknox.absa.AbsaApplication;
import com.nobbyknox.absa.queues.Queues;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class StatusService {

    private Logger logger = Logger.getLogger(StatusService.class.getName());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void validateMessage(byte[] message) throws Exception {
    }

    public void progressFlow(byte[] message) {
        logger.info("Processing message");

        if (mockStatusCheck()) {
            // Send further down the line
            Message rabbitMessage = new Message(message, new MessageProperties());
            rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.ENGINE.toString(), rabbitMessage);
        }

    }

    private boolean mockStatusCheck() {
        return true;
    }

}
