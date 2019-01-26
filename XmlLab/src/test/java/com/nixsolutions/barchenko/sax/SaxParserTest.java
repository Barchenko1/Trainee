package com.nixsolutions.barchenko.sax;

import com.nixsolutions.barchenko.dom.DomParser;
import org.custommonkey.xmlunit.Diff;
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
import javax.xml.parsers.SAXParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class SaxParserTest {

    private static final String SOURCE_XML = "src/main/resources/Sax.xml";
    private static final String OUT_XML = "src/main/resources/OutSax.xml";
    private static final String EXPECT_XML = "src/main/resources/Expect.xml";
    private static final String WRONG_XML = "src/main/resources/Wrong.xml";

    @Before
    public void setUp() {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void saxXmlParserTest() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document example = builder.parse(new File(EXPECT_XML));

        SaxParser saxParser = new SaxParser(SOURCE_XML, OUT_XML);
        saxParser.xmlParser();

        Document result = builder.parse(new File(
                OUT_XML));

        XMLAssert.assertXMLEqual(example, result);
    }

    @Test(expected = FileNotFoundException.class)
    public void saxParserFileNotFound()
            throws Exception {
        SaxParser parser = new SaxParser("src/main/none", OUT_XML);
        parser.xmlParser();
    }

    @Test(expected = SAXParseException.class)
    public void saxParserWrongFormat()
            throws Exception {
        SaxParser parser = new SaxParser(WRONG_XML, OUT_XML);
        parser.xmlParser();
    }

}