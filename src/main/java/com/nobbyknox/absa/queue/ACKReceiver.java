package com.nobbyknox.absa.queue;

import com.nobbyknox.absa.service.ACKService;
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
