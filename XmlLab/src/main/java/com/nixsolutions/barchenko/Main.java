package com.nixsolutions.barchenko;

import com.nixsolutions.barchenko.dom.DomParser;
import com.nixsolutions.barchenko.sax.SaxParser;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class Main {
    public static void main(String[] args)
            throws IOException, SAXException, ParserConfigurationException {
        String inDom = "src/main/resources/Dom.xml";
        String outDom = "src/main/resources/OutDom.xml";

        String inSax = "src/main/resources/Sax.xml";
        String outSax = "src/main/resources/OutSax.xml";

        DomParser domParser = new DomParser(inDom, outDom);
        try {
            domParser.xmlParser();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        
        SaxParser saxParser = new SaxParser(inSax, outSax);
        saxParser.xmlParser();
        saxParser.writeToFile();

    }
}
