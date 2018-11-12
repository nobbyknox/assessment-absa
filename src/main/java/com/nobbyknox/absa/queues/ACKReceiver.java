package com.nobbyknox.absa.queues;

import com.nobbyknox.absa.services.ACKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ACKReceiver {

    @Autowired
    ACKService service;

    public void receiveMessage(byte[] message) {
        try {
            service.validateMessage(message);
            service.progressFlow();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
