package com.nobbyknox.absa.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class XmlUtilTests {

    private final String xmlString = "<?xml version=\"1.0\"?> <company> <staff> <firstname>John</firstname> <lastname>Hickok</lastname> </staff> </company>";

    @Test
    public void shouldSerializeXMLDoc() {
        try {
            Document doc = XmlUtil.deserializeXml(xmlString.getBytes(StandardCharsets.UTF_8));
            assertNotNull(doc);

            byte[] array = XmlUtil.serializeXml(doc);

            assertNotNull(array);
            assertTrue(array.length > 0);
        } catch (Exception exc) {
            fail(exc.getMessage());
        }
    }

    @Test
    public void shouldDeserializeXMLDoc() {
        Document doc = XmlUtil.deserializeXml(xmlString.getBytes(StandardCharsets.UTF_8));
        assertNotNull(doc);
    }
}
