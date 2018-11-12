package com.nobbyknox.absa.services;

import com.nobbyknox.absa.AbsaApplication;
import com.nobbyknox.absa.entities.RoutingRule;
import com.nobbyknox.absa.queues.Queues;
import com.nobbyknox.absa.utils.XmlUtil;
import org.hibernate.jpa.internal.util.XmlHelper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class StatusService {

    private Logger logger = Logger.getLogger(StatusService.class.getName());

    @Value("${app.endpoint.routingRules}")
    private String endpoint;

    @Value("${app.routingRule.id}")
    private int ruleId;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void validateMessage(byte[] message) throws Exception {
        ValidationHelper.validateXmlMessage(message);
    }

    public void progressFlow(byte[] message) {
        logger.info("Processing message");

        Document doc = XmlUtil.deserializeXml(message);

        if (doc == null) {
            // NOTE: How should we handle this?
            // Still waiting for feedback from Systems Analyst.
        }

        MessageStatus status = getMessageStatus(doc);

        Message rabbitMessage = new Message(XmlUtil.serializeXml(doc), new MessageProperties());

        // According to specification, we send to MT195 ACK step regardless of the status
        rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.ACK.toString(), rabbitMessage);

        if (status == MessageStatus.APPROVED) {
            setDestination(doc, getDestination());
            rabbitMessage = new Message(XmlUtil.serializeXml(doc), new MessageProperties());
            rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.ENGINE.toString(), rabbitMessage);
        }

    }

    private MessageStatus getMessageStatus(Document doc) {
        MessageStatus status = MessageStatus.APPROVED;

        NodeList nodes = doc.getElementsByTagName("meta");
        Node metaNode = nodes.item(0);
        NodeList children = metaNode.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeName().equals("status")) {
                status = MessageStatus.getStatus(children.item(i).getTextContent());
            }
        }

        return status;
    }

    private void setDestination(Document doc, String destination) {
        NodeList nodes = doc.getElementsByTagName("meta");
        Node metaNode = nodes.item(0);
        NodeList children = metaNode.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeName().equals("destination")) {
                children.item(i).setTextContent(destination);
            }
        }
    }

    private String getDestination() {
        String dest = "None";

        logger.info("endpoint: " + endpoint);
        logger.info("ruleId: " + ruleId);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<RoutingRule>> response = restTemplate.exchange(endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<List<RoutingRule>>() { });
        List<RoutingRule> rules = response.getBody();


        if (rules.size() > 0) {
            logger.info("rules: " + rules.toString());

            // Randomly select a rule and test against the criteria.
            // If no match, then reject the message.
            Optional<RoutingRule> optionalRule = rules.stream().filter(item -> item.id == ruleId).findFirst();

            if (optionalRule.isPresent()) {
                RoutingRule rule = optionalRule.get();
                logger.info("Found rule: " + rule);
                dest = rule.destination;
            } else {
                logger.severe("Unable to determine destination engine. Going with " + dest + " for now");
            }
        }

        logger.info("dest: " + dest);

        return dest;
    }

/*
    private boolean getMessageStatus() {
        boolean rejected = false;

        logger.info("endpoint: " + endpoint);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<RoutingRule>> response = restTemplate.exchange(endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<List<RoutingRule>>() { });
        List<RoutingRule> rules = response.getBody();


        if (rules.size() == 0) {
            rejected = true;
        } else {
            logger.info("rules: " + rules.toString());

            // Randomly select a rule and test against the criteria.
            // If no match, then reject the message.
            Optional<RoutingRule> optionalRule = rules.stream().findFirst();

            if (optionalRule.isPresent()) {
                RoutingRule rule = optionalRule.get();
                logger.info("Found rule: " + rule);
                rejected = !rule.routeData.equals(successRule);
            } else {
                // TODO: Handle this

                // TODO: Continue here
            }

        }

        logger.info("rejected: " + rejected);

        return rejected;
    }
*/

/*
    private void setMessageStatus(Document doc, MessageStatus status) {
        NodeList nodes = doc.getElementsByTagName("meta");
        Node metaNode = nodes.item(0);
        NodeList children = metaNode.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeName().equals("status")) {
                children.item(i).setTextContent(status.toString());
            }
        }
    }
*/

}
