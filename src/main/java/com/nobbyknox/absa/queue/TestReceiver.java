package com.nobbyknox.absa.queue;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
//@Profile("production")
public class TestReceiver {
    public void receiveMessage(String message) {
        System.out.println("[testReceiver] Received: " + message);
    }

    public void receiveMessage(byte[] message) {
        receiveMessage(new String(message, StandardCharsets.UTF_8));
    }
}
