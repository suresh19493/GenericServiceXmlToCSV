package com.generic.xml2csv.service;

import javax.xml.transform.TransformerFactoryConfigurationError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.generic.xml2csv.util.RestUtil;
import com.generic.xml2csv.util.XmlUtil;

@Service
public class Xml2CsvService {
	private static final Logger LOGGER = LoggerFactory.getLogger(Xml2CsvService.class);

	@Autowired
	private RestUtil restUtil;
	@Value("${csvFileName}")
	private String csvFileName;
	public void getCsvFromXmlURL(@RequestParam("xmlUrl") String xmlUrl)
			throws TransformerFactoryConfigurationError, Exception {
		LOGGER.info("xmlURL:" + xmlUrl);
		String xmlResource = restUtil.invokeGetMethod(xmlUrl);
		LOGGER.info("xmlResource:" + xmlResource);
		XmlUtil.convertStringToXMLDocument(xmlResource);
	}
}
