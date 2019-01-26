package com.nixsolutions.barchenko.sax;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxParser {

    private String sourceFile;
    private String destFile;
    private MyHandler myHandler = new MyHandler();

    public SaxParser() {

    }

    public SaxParser(String sourceFile, String destFile) {
        this.sourceFile = sourceFile;
        this.destFile = destFile;
    }

    public void xmlParser()
            throws ParserConfigurationException, org.xml.sax.SAXException,
            IOException {
        File inFile = new File(sourceFile);
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setValidating(false);
        SAXParser saxParser = saxParserFactory.newSAXParser();
        saxParser.parse(inFile, myHandler);
        writeToFile();
    }

    public void writeToFile() {
        try (FileWriter filewriter = new FileWriter(destFile)) {
            filewriter.write(myHandler.getResult().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

