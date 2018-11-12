package com.nobbyknox.absa.services;

import com.nobbyknox.absa.utils.XmlUtil;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.util.logging.Logger;

@Service
public class ACKService {
    private Logger logger = Logger.getLogger(ACKService.class.getName());

    public void validateMessage(byte[] message) throws Exception {
        ValidationHelper.validateXmlMessage(message);
    }

    public void advanceFlow(byte[] message) {
        logger.info("Payment successfully processed. End of workflow reached.");
        sendMT195Message(XmlUtil.deserializeXml(message));
    }

    private void sendMT195Message(Document doc) {
        logger.info("Converting XML document to MT195 format and sending it on");
        logger.info("(conversion and sending not shown)");
        logger.info("Payment message processing complete");
    }
}
