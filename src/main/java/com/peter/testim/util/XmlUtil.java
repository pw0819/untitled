package com.peter.testim.util;

import java.io.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: test
 * Date: 14-2-25
 * Time: 下午4:05
 * To change this template use File | Settings | File Templates.
 */
@Service
public class XmlUtil {
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public Marshaller getMarshaller() {
        return marshaller;
    }

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public void convertFromObjectToXML(Object object, String xml)
            throws IOException {

        FileOutputStream os = null;

        try {
            getMarshaller().marshal(object, new StreamResult(xml));
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    public Object convertFromXMLToObject(String xml) throws IOException {

        FileInputStream is = null;
        try {
            return getUnmarshaller().unmarshal(new StreamSource(xml));
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
