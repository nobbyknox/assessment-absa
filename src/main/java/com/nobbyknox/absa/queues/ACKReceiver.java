package com.nobbyknox.absa.queues;

import com.nobbyknox.absa.services.ACKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ACKReceiver {

    @Autowired
    private ACKService service;

    public void receiveMessage(byte[] message) {
        try {
            service.validateMessage(message);
            service.advanceFlow(message);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
