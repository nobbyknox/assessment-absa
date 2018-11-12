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
            service.progressFlow(message);
        } catch (Exception exc) {
            exc.printStackTrace();
        }

/*
        Document doc = XmlUtil.deserializeXml(message);

        System.out.println("Got XML doc for status check");

        String whichEngine = mockGetEngine();

        if (whichEngine.equals("ABC")) {
            // Send further down the line
            Message rabbitMessage = new Message(message, new MessageProperties());
            rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.ACK.toString(), rabbitMessage);
        }
*/
    }

//    private String mockGetEngine() {
//        return "ABC";
//    }
}
