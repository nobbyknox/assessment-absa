package com.nobbyknox.absa.utils;

import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class MockMT101Parser {
    public static Document parseMessage(String mt101) {
        try {
            File file = ResourceUtils.getFile("classpath:payment-request.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            return dBuilder.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
