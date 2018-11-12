package com.nobbyknox.absa.queue;

import com.nobbyknox.absa.AbsaApplication;
import com.nobbyknox.absa.util.MockMT101Parser;
import com.nobbyknox.absa.util.XmlUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.nio.charset.StandardCharsets;

@Component
public class PaymentReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    public void receiveMessage(String message) {
//        System.out.println("[PaymentReceiver] Received: " + message);
//        rabbitTemplate.convertAndSend(AbsaApplication.topicExchangeName, Queues.STATUS.toString(), "Please check status");
//    }

    public void receiveMessage(byte[] message) {
        Document doc = MockMT101Parser.parseMessage(new String(message, StandardCharsets.UTF_8));

        if (doc == null) {
            System.out.println("Unable to parse MT101 into XML format");
            return;
        }

        // https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/

        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("sender");

        System.out.println("sender getNodeName: " + nList.item(0).getNodeName());
        System.out.println("ref getNodeName: " + ((Element) nList.item(0)).getElementsByTagName("ref").item(0).getNodeName());
        System.out.println("ref getTextContent: " + ((Element) nList.item(0)).getElementsByTagName("ref").item(0).getTextContent());

        System.out.println("Sending MT101 converted message, now in XML, for status check");

        Message rabbitMessage = new Message(XmlUtil.serializeXml(doc), new MessageProperties());
        rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.STATUS.toString(), rabbitMessage);
    }
}
