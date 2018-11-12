package com.nobbyknox.absa.service;

import com.nobbyknox.absa.AbsaApplication;
import com.nobbyknox.absa.queue.Queues;
import com.nobbyknox.absa.util.MockMT101Parser;
import com.nobbyknox.absa.util.XmlUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.nio.charset.StandardCharsets;

@Service
public class PaymentService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void validateMT101Message(byte[] mt101) throws Exception {
        if (mt101 == null) {
            throw new Exception("MT101 message is null");
        }
    }

    public void progressFlow(byte[] message) {

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
