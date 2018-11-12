package com.nobbyknox.absa.service;

import com.nobbyknox.absa.AbsaApplication;
import com.nobbyknox.absa.queue.Queues;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EngineService {

    private Logger logger = Logger.getLogger(EngineService.class.getName());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void validateMessage(byte[] message) throws Exception {
    }

    public void progressFlow(byte[] message) {
//        Document doc = XmlUtil.deserializeXml(message);
//        logger.info("Got XML doc for status check");

        logger.info("Processing message");

        String whichEngine = mockGetEngine();

        if (whichEngine.equals("ABC")) {
            // Send further down the line
            Message rabbitMessage = new Message(message, new MessageProperties());
            rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.ACK.toString(), rabbitMessage);
        } else {
            // TODO: What happens here?
        }
    }

    private String mockGetEngine() {
        return "ABC";
    }
}
