package com.nobbyknox.absa.services;

import com.nobbyknox.absa.AbsaApplication;
import com.nobbyknox.absa.entities.RoutingRule;
import com.nobbyknox.absa.queues.Queues;
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

import java.util.List;
import java.util.logging.Logger;

@Service
public class StatusService {

    private Logger logger = Logger.getLogger(StatusService.class.getName());

    @Value("${app.endpoint.routingRules}")
    private String endpoint;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void validateMessage(byte[] message) throws Exception {
    }

    public void progressFlow(byte[] message) {
        logger.info("Processing message");

        boolean rejected = getMessageStatus();

        if (rejected) {
            // TODO: Handle rejection
        } else {
            // Send further down the line
            Message rabbitMessage = new Message(message, new MessageProperties());
            rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.ENGINE.toString(), rabbitMessage);
        }

    }

    private boolean getMessageStatus() {
        boolean rejected = false;

        logger.info("endpoint: " + endpoint);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<RoutingRule>> response = restTemplate.exchange(endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<List<RoutingRule>>() { });
        List<RoutingRule> rules = response.getBody();

        logger.info("rules: " + rules.toString());

        if (rules.size() == 0) {
            rejected = true;
        } else {
            RoutingRule rule = rules.stream().findFirst().get();

            logger.info("Found rule: " + rule);

            rejected = rule.routeData.equals("123");
        }

        logger.info("rejected: " + rejected);

        // TODO: Expand
        return rejected;
    }

}
