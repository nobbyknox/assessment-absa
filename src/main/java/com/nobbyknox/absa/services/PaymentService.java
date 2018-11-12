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

    public void advanceFlow(byte[] message) {
        Document doc = MockMT101Parser.parseMessage(new String(message, StandardCharsets.UTF_8));

        if (doc == null) {
            logger.severe("Unable to parse MT101 into XML format");
            return;
        }

        Message rabbitMessage = new Message(XmlUtil.serializeXml(doc), new MessageProperties());
        rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.STATUS.toString(), rabbitMessage);

    }
}
