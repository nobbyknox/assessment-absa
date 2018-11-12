package com.nobbyknox.absa.queue;

import com.nobbyknox.absa.AbsaApplication;
import com.nobbyknox.absa.util.XmlUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Component
//@Profile("production")
public class StatusReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    public void receiveMessage(String message) {
//        System.out.println("[StatusReceiver] Received: " + message);
//        rabbitTemplate.convertAndSend(AbsaApplication.topicExchangeName, Queues.ENGINE.toString(), "Engine thing");
//    }

    public void receiveMessage(byte[] message) {
        Document doc = XmlUtil.deserializeXml(message);

        System.out.println("Got XML doc for status check");

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
