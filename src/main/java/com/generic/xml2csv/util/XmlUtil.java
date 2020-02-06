package com.generic.xml2csv.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtil.class);
	private static final String CSV_SEPERATOR = ",";
	private static final String NEW_LINE_CHAR = "\n";
	private static final String CSV_EXTENSION = ".csv";

	public static String convertStringToXMLDocument(String xmlData)
			throws TransformerFactoryConfigurationError, Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new ByteArrayInputStream(xmlData.getBytes("utf-8"))));
		Element element = document.getDocumentElement();

		String nodeName = element.getNodeName();
		LOGGER.debug("Root element of the document: " + nodeName);

		NodeList nodeListOfDocument = element.getElementsByTagName(element.getNodeName());
		LOGGER.info("The total elements of the xml source data as nodeList(number of rows):"
				+ nodeListOfDocument.getLength());
		List<String> elements = getElementsInTheNode(nodeListOfDocument);
		LOGGER.info("Elements in the node:" + elements);
		String fileName = nodeName + CSV_EXTENSION;
		generateCsvFile(fileName, elements, nodeListOfDocument);
		return fileName;

	}

	private static List<String> getElementsInTheNode(NodeList nodeListOfDocument) {
		List<String> elements = new ArrayList<>();

		if (nodeListOfDocument != null && nodeListOfDocument.getLength() > 0) {
			// for (int i = 0; i < nodeListOfDocument.getLength(); i++) {
			Node node = nodeListOfDocument.item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				LOGGER.debug("=====================");
				Element e = (Element) node;
				NodeList childNodes = e.getChildNodes();
				LOGGER.debug("childNodes: " + childNodes);
				LOGGER.debug("childNodes length: " + childNodes.getLength());

				for (int j = 0; j < childNodes.getLength(); j++) {
					String childNodeName = childNodes.item(j).getNodeName();
					LOGGER.debug("childNodeName: " + childNodeName);
					elements.add(childNodeName);
				}
			}
		}
		return elements;
	}

	private static void generateCsvFile(String fileName, List<String> elements, NodeList nodeListOfDocument)
			throws Exception {
		File file = new File(fileName);
		try (FileWriter fw = new FileWriter(file)) {
			StringBuffer rowHeader = new StringBuffer();
			elements.forEach(value -> {
				rowHeader.append(value);
				rowHeader.append(CSV_SEPERATOR);
			});
			LOGGER.info("rowHeader:" + rowHeader.toString());
			rowHeader.append(NEW_LINE_CHAR);
			fw.append(rowHeader.toString());
			if (nodeListOfDocument != null && nodeListOfDocument.getLength() > 0) {
				for (int i = 0; i < nodeListOfDocument.getLength(); i++) {
					Node node = nodeListOfDocument.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						LOGGER.debug("=====================");
						Element e = (Element) node;
						elements.forEach(childNodeName -> {
							NodeList nodeList = e.getElementsByTagName(childNodeName);

							if (null != nodeList && null != nodeList.item(0) && null != nodeList.item(0).getChildNodes()
									&& null != nodeList.item(0).getChildNodes().item(0)
									&& null != nodeList.item(0).getChildNodes().item(0).getNodeValue()) {
								String childNodeValue = nodeList.item(0).getChildNodes().item(0).getNodeValue();
								try {
									fw.append(childNodeValue);
									fw.append(CSV_SEPERATOR);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							} else {

								try {
									fw.append("");
									fw.append(CSV_SEPERATOR);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						});
						fw.append(NEW_LINE_CHAR);

					}
				}
			} else {
				LOGGER.warn("No elements found");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			throw e;
		}
	}

	public static void main(String[] args) throws TransformerFactoryConfigurationError, Exception {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(
				"https://datenservice.net-connect-germany.de/XmlInterface/getXML.ashx?ReportId=Linepack&Start=20-01-2020",
				String.class);
		String xmlData = responseEntity.getBody();
		System.out.println("xmlData:" + xmlData);
		convertStringToXMLDocument(xmlData);
	}
}
