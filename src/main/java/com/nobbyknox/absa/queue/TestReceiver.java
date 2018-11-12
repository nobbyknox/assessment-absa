package com.nobbyknox.absa.queue;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class TestReceiver {
    public void receiveMessage(String message) {
        System.out.println("[testReceiver] Received: " + message);
    }

    public void receiveMessage(byte[] message) {
        receiveMessage(new String(message, StandardCharsets.UTF_8));
    }
}
