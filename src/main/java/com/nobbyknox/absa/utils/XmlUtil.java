package com.nobbyknox.absa.utils;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Various XML utility methods
 */
public class XmlUtil {

    /**
     * Serialize an XML document to a byte array
     *
     * @param doc XML document
     * @return serialized byte array
     */
    public static byte[] serializeXml(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(bos);
            transformer.transform(source, result);
            return bos.toByteArray();
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return null;
    }

    /**
     * Deserialize byte array into XML document
     *
     * @param byteArray incoming byte array representation of XML document
     * @return XML document
     */
    public static Document deserializeXml(byte[] byteArray) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new ByteArrayInputStream(byteArray));
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return null;
    }
}
