package com.generic.xml2csv.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.transform.TransformerFactoryConfigurationError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
	public ResponseEntity<Resource> getCsvFromXmlURL(@RequestParam("xmlUrl") String xmlUrl)
			throws TransformerFactoryConfigurationError, Exception {
		LOGGER.info("xmlURL:" + xmlUrl);
		String fileName = xml2CsvService.getCsvFromXmlURL(xmlUrl);
		File file = new File(fileName);
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		// return ResponseEntity.accepted().body("Succesfully processed the data");
	}
}
