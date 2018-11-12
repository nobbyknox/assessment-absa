package com.nobbyknox.absa.queues;

import com.nobbyknox.absa.AbsaApplication;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Component
@Profile("!test")
public class Sender implements CommandLineRunner {

    private Logger logger = Logger.getLogger(Sender.class.getName());

    private final RabbitTemplate rabbitTemplate;
    private final PaymentReceiver paymentReceiver;

    public Sender(PaymentReceiver paymentReceiver, RabbitTemplate rabbitTemplate) {
        this.paymentReceiver = paymentReceiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Sending MT101 message...");
        String mt101 = getMT101Message();

        if (mt101 == null) {
            logger.severe("Unable to load MT101 message");
            return;
        }

//        Document xml = MockMT101Parser.parseMessage(mt101);
//
//        if (xml == null) {
//            System.out.println("Unable to parse MT101 into XML format");
//            return;
//        }

        Message mesg = new Message(mt101.getBytes(StandardCharsets.UTF_8), new MessageProperties());
        rabbitTemplate.send(AbsaApplication.topicExchangeName, Queues.PAYMENT.toString(), mesg);
    }

    private String getMT101Message() {
        try {
            return new String(Files.readAllBytes(Paths.get(ResourceUtils.getFile("classpath:mt101.txt").getPath())), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
