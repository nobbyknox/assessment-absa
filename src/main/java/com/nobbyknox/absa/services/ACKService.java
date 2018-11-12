package com.nobbyknox.absa.services;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ACKService {
    private Logger logger = Logger.getLogger(ACKService.class.getName());

    public void validateMessage(byte[] message) throws Exception {
        ValidationHelper.validateXmlMessage(message);
    }

    public void progressFlow() {
        logger.info("Payment successfully processed. End of workflow reached.");
    }
}
