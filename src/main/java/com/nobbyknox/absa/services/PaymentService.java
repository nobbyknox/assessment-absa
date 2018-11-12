package com.nobbyknox.absa.services;

import com.nobbyknox.absa.AbsaApplication;
import com.nobbyknox.absa.queues.Queues;
import com.nobbyknox.absa.utils.MockMT101Parser;
import com.nobbyknox.absa.utils.XmlUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Service
public class PaymentService {

    private Logger logger = Logger.getLogger(PaymentService.class.getName());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void validateMessage(byte[] message) throws Exception {
        ValidationHelper.validateMT101Message(message);
    }

    public void progressFlow(byte[] message) {

        logger.info("Processing message");

        Document doc = MockMT101Parser.parseMessage(new String(message, StandardCharsets.UTF_8));

        if (doc == null) {
//            System.out.println("Unable to parse MT101 into XML format");
            logger.severe("Unable to parse MT101 into XML format");
            return;
        }

        // https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/

        logger.info("Root element: " + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("sender");

        logger.info("sender getNodeName: " + nList.item(0).getNodeName());
        logger.info("ref getNodeName: " + ((Element) nList.item(0)).getElementsByTagName("ref").item(0).getNodeName());
        logger.info("ref getTextContent: " + ((Element) nList.item(0)).getElementsByTagName("ref").item(0).getTextContent());

        logger.info("Sending MT101 converted message, now in XML, for status check");

        Message rabbitMessage = new Message(XmlUtil.serializeXml(doc), new MessageProperties());
        rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.STATUS.toString(), rabbitMessage);

    }
}
