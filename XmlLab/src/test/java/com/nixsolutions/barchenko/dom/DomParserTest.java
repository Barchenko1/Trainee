package com.nixsolutions.barchenko.dom;

import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class DomParserTest {

    private static final String SOURCE_XML = "src/main/resources/Dom.xml";
    private static final String OUT_XML = "src/main/resources/OutDom.xml";
    private static final String EXPECT_XML = "src/main/resources/Expect.xml";
    private static final String WRONG_XML = "src/main/resources/Wrong.xml";

    @Before
    public void setUp()
            throws ParserConfigurationException, IOException, SAXException {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void domXmlParserTest() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document example = builder.parse(new File(EXPECT_XML));

        DomParser domParser = new DomParser(SOURCE_XML, OUT_XML);
        domParser.xmlParser();
        Document result = builder.parse(new File(
                OUT_XML));

        XMLAssert.assertXMLEqual(example, result);
    }

    @Test(expected = FileNotFoundException.class)
    public void domParserFileNotFound()
            throws Exception {
        DomParser parser = new DomParser("src/main/none", OUT_XML);
        parser.xmlParser();
    }

    @Test(expected = SAXParseException.class)
    public void domParserWrongFormat()
            throws Exception {
        DomParser parser = new DomParser(WRONG_XML, OUT_XML);
        parser.xmlParser();
    }
}