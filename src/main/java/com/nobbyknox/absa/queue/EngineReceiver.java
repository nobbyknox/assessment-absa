package com.nobbyknox.absa.queue;

import com.nobbyknox.absa.AbsaApplication;
import com.nobbyknox.absa.util.XmlUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.nio.charset.StandardCharsets;

@Component
public class EngineReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    public void receiveMessage(String message) {
//        System.out.println("[EngineReceiver] Received: " + message);
//        rabbitTemplate.convertAndSend(AbsaApplication.topicExchangeName, Queues.ACK.toString(), "ACK");
//    }

    public void receiveMessage(byte[] message) {
        Document doc = XmlUtil.deserializeXml(message);

        System.out.println("Got XML doc for status check");

        String whichEngine = mockGetEngine();

        if (whichEngine.equals("ABC")) {
            // Send further down the line
            Message rabbitMessage = new Message(message, new MessageProperties());
            rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.ACK.toString(), rabbitMessage);
        }
    }

    private String mockGetEngine() {
        return "ABC";
    }
}
