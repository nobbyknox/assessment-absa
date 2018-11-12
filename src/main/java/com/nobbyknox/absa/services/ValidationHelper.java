package com.nobbyknox.absa.services;

import com.nobbyknox.absa.utils.XmlUtil;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class ValidationHelper {

    public static void validateMT101Message(byte[] message) throws Exception {
        if (message == null) {
            throw new Exception("MT101 message is null");
        }

        // Other MT101 message validation can be written here
    }

    public static void validateXmlMessage(byte[] message) throws Exception {
        if (message == null) {
            throw new Exception("XML message is null");
        }

        // This is not the most optimized code, but we are trying to be thorough
        // and precise, not fast.
        Document doc = XmlUtil.deserializeXml(message);

        if (doc == null) {
            throw new Exception("Unable to convert message to XML document");
        }

        // You may validate the XML document further here
    }
}
