package com.nobbyknox.absa;

import com.nobbyknox.absa.queues.*;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class AbsaApplication {

    public static final String topicExchangeName = "payment.exchange";

    @Bean
    Queue paymentQueue() {
        return new Queue(Queues.PAYMENT.toString(), false, true, true);
    }

    @Bean
    Queue statusQueue() {
        return new Queue(Queues.STATUS.toString(), false, true, true);
    }

    @Bean
    Queue engineQueue() {
        return new Queue(Queues.ENGINE.toString(), false, true, true);
    }

    @Bean
    Queue ackQueue() {
        return new Queue(Queues.ACK.toString(), false, true, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding paymentBinding(TopicExchange exchange) {
        return BindingBuilder.bind(paymentQueue()).to(exchange).with(Queues.PAYMENT.toString());
    }

    @Bean
    Binding statusBinding(TopicExchange exchange) {
        return BindingBuilder.bind(statusQueue()).to(exchange).with(Queues.STATUS.toString());
    }

    @Bean
    Binding engineBinding(TopicExchange exchange) {
        return BindingBuilder.bind(engineQueue()).to(exchange).with(Queues.ENGINE.toString());
    }

    @Bean
    Binding ackBinding(TopicExchange exchange) {
        return BindingBuilder.bind(ackQueue()).to(exchange).with(Queues.ACK.toString());
    }

    @Bean
    @Profile("!test")
    SimpleMessageListenerContainer paymentContainer(ConnectionFactory connectionFactory, @Qualifier("paymentListenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(Queues.PAYMENT.toString());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    @Profile("!test")
    SimpleMessageListenerContainer statusContainer(ConnectionFactory connectionFactory, @Qualifier("statusListenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(Queues.STATUS.toString());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    @Profile("!test")
    SimpleMessageListenerContainer engineContainer(ConnectionFactory connectionFactory, @Qualifier("engineListenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(Queues.ENGINE.toString());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    @Profile("!test")
    SimpleMessageListenerContainer ackContainer(ConnectionFactory connectionFactory, @Qualifier("ackListenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(Queues.ACK.toString());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter paymentListenerAdapter(PaymentReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    MessageListenerAdapter statusListenerAdapter(StatusReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    MessageListenerAdapter engineListenerAdapter(EngineReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    MessageListenerAdapter ackListenerAdapter(ACKReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

	public static void main(String[] args) {
		SpringApplication.run(AbsaApplication.class, args);
	}
}
