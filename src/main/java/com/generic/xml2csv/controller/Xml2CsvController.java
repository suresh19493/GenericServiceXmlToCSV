package com.generic.xml2csv.controller;

import javax.xml.transform.TransformerFactoryConfigurationError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generic.xml2csv.service.Xml2CsvService;

@RestController
public class Xml2CsvController {
	private static final Logger LOGGER = LoggerFactory.getLogger(Xml2CsvController.class);
	@Autowired
	private Xml2CsvService xml2CsvService;

	@GetMapping(value = "xmlToCsv")
	public ResponseEntity<String> getCsvFromXmlURL(@RequestParam("xmlUrl") String xmlUrl) throws TransformerFactoryConfigurationError, Exception {
		LOGGER.info("xmlURL:" + xmlUrl);
		xml2CsvService.getCsvFromXmlURL(xmlUrl);	
		return ResponseEntity.accepted().body("Succesfully processed the data");				
	}
}
