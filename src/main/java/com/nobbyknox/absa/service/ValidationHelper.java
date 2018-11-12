package com.nobbyknox.absa.service;

import org.springframework.stereotype.Service;

@Service
public class ValidationHelper {
    public static void validateMT101Message(byte[] message) throws Exception {
        if (message == null) {
            throw new Exception("MT101 message is null");
        }
    }

    public static void validateXmlMessage(byte[] message) throws Exception {
    }
}
