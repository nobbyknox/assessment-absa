package com.nobbyknox.absa.queues;

import com.nobbyknox.absa.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@Profile("production")
public class StatusReceiver {

    @Autowired
    private StatusService service;

//    public void receiveMessage(String message) {
//        System.out.println("[StatusReceiver] Received: " + message);
//        rabbitTemplate.convertAndSend(AbsaApplication.topicExchangeName, Queues.ENGINE.toString(), "Engine thing");
//    }

    public void receiveMessage(byte[] message) {
        try {
            service.validateMessage(message);
            service.progressFlow(message);
        } catch (Exception exc) {
            exc.printStackTrace();
        }

//        Document doc = XmlUtil.deserializeXml(message);
//        System.out.println("Got XML doc for status check");

//        if (mockStatusCheck()) {
//            // Send further down the line
//            Message rabbitMessage = new Message(message, new MessageProperties());
//            rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.ENGINE.toString(), rabbitMessage);
//        }
    }

//    private boolean mockStatusCheck() {
//        return true;
//    }
}
