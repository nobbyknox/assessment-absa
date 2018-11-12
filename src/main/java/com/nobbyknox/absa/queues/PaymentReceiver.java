package com.nobbyknox.absa.queues;

import com.nobbyknox.absa.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentReceiver {

    @Autowired
    private PaymentService service;

    public void receiveMessage(byte[] message) {
        try {
            service.validateMessage(message);
            service.advanceFlow(message);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
