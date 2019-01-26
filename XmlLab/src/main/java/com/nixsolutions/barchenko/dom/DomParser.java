package com.nixsolutions.barchenko.dom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomParser {

    private String inPath;
    private String outPath;

    public DomParser(String inPath, String outPath) {
        this.inPath = inPath;
        this.outPath = outPath;
    }

    public DomParser() {
    }

    public File xmlParser()
            throws ParserConfigurationException, IOException, SAXException,
            TransformerException {
        File file = new File(inPath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        if (document.hasChildNodes()) {
            deleteNode(document.getFirstChild());
        }
        return xmlWriter(document);
    }

    private void deleteNode(Node node) {
        int counter = 1;
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            short nodeType = list.item(i).getNodeType();
            if (nodeType == Node.ELEMENT_NODE) {
                if (counter++ % 2 == 0) {
                    node.removeChild(list.item(i));
                } else {
                    deleteNode(list.item(i));
                }
            }
        }
    }

    private File xmlWriter(Document document) throws IOException,
            TransformerException {
        File result = new File(outPath);
        Source source = new DOMSource(document);

        FileWriter fileWriter = new FileWriter(result);
        StreamResult stream = new StreamResult(fileWriter);

        TransformerFactory transformerFactory =
                TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, stream);
        return result;
    }
}
