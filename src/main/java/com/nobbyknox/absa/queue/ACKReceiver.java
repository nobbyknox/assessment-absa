package com.nobbyknox.absa.queue;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class ACKReceiver {

    public void receiveMessage(String message) {
        System.out.println("[ACKReceiver] Received: " + message);
        System.out.println("Payment successfully processed. Going home now.");
    }

    public void receiveMessage(byte[] message) {
        receiveMessage(new String(message, StandardCharsets.UTF_8));
    }
}
