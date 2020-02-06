package com.generic.xml2csv.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestUtil {
	
	@Autowired RestTemplate restTemplate;
	public String invokeGetMethod(String url) {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		return responseEntity.getBody();
	}

}
