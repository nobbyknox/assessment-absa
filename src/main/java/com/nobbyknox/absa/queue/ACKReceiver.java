package com.nobbyknox.absa.queue;

import org.springframework.stereotype.Component;

@Component
public class ACKReceiver {

//    public void receiveMessage(String message) {
//        System.out.println("[ACKReceiver] Received: " + message);
//        System.out.println("Payment successfully processed. Going home now.");
//    }

    public void receiveMessage(byte[] message) {
        System.out.println("[ACKReceiver] Payment successfully processed. Going home now.");
    }
}
