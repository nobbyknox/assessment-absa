package com.nobbyknox.absa.services;

import com.nobbyknox.absa.AbsaApplication;
import com.nobbyknox.absa.queues.Queues;
import com.nobbyknox.absa.utils.XmlUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.logging.Logger;

@Service
public class EngineService {

    private Logger logger = Logger.getLogger(EngineService.class.getName());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void validateMessage(byte[] message) throws Exception {
        ValidationHelper.validateXmlMessage(message);
    }

    public void progressFlow(byte[] message) {
        Document doc = XmlUtil.deserializeXml(message);

        if (doc == null) {
            // NOTE: What do we do? Ignoring issue for now.
        }

        String destination = getDestination(doc);

        logger.info("Destination: " + destination);

        if (destination.equals("None")) {
            targetSettlementEngineAdapter(doc);
        } else {
            targetSettlementEngineAdapterWithAckNak(doc);
        }
    }

    private String getDestination(Document doc) {
        String destination = "None";
        NodeList nodes = doc.getElementsByTagName("meta");
        Node metaNode = nodes.item(0);
        NodeList children = metaNode.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeName().equals("destination")) {
                destination = children.item(i).getTextContent();
            }
        }

        return destination;
    }

    private void targetSettlementEngineAdapter(Document doc) {
        logger.info("Targeting settlement engine adapter...");
        // Do import settlement work here and then just end as there is nothing left to do
        logger.info("Payment message processing complete");
    }

    private void targetSettlementEngineAdapterWithAckNak(Document doc) {
        logger.info("Targeting settlement engine adapter with AckNak...");
        Message rabbitMessage = new Message(XmlUtil.serializeXml(doc), new MessageProperties());
        rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.ACK.toString(), rabbitMessage);
    }

}
