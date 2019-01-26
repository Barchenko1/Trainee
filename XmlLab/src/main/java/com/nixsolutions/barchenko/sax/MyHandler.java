package com.nixsolutions.barchenko.sax;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {

    private StringBuilder result = new StringBuilder();
    private StringBuilder tempSb = new StringBuilder();

    public StringBuilder getResult() {
        return result;
    }

    private boolean startTag;
    private boolean endTag;
    private List<Boolean> depth = new ArrayList<>();

    @Override
    public void startDocument() throws SAXException {
        result.append(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
    }

    @Override
    public void startElement(String uri, String localName,
            String qName, Attributes attributes) throws SAXException {

        if (depth.isEmpty() || startTag) {
            depth.add(true);
        } else {
            depth.set(depth.size() - 1, !depth.get(depth.size() - 1));
        }
        if (checkEven()) {
            result.append(System.lineSeparator())
                    .append(tempSb).append("<" + qName + ">");
        }
        startTag = true;
        endTag = false;
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (checkEven()) {
            String str = new String(ch, start, length).trim();
            if (str.length() > 0) {
                result.append(str);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (endTag) {
            depth.remove(depth.size() - 1);
            if (checkEven()) {
                result.delete(0, tempSb.length());
                result.append(tempSb);
                result.append("</" + qName + ">")
                        .append(System.lineSeparator());
            }
        } else if (checkEven()) {
            result.append("</" + qName + ">").append(System.lineSeparator());
            tempSb.delete(0, tempSb.length());
        }
        startTag = false;
        endTag = true;
    }

    //проверка в коллекции
    private boolean checkEven() {
        boolean isEvan = true;
        for (Boolean b : depth) {
            if (b == false) {
                isEvan = false;
            }
        }
        return isEvan;
    }
}
