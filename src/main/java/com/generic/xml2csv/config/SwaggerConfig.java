package com.generic.xml2csv.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		Contact contact = new Contact("Suresh Emmadisetti", "http://www.demo.com", "developer@appsdeveloperblog.com");
		List<VendorExtension> vendorExtensions = new ArrayList<>();
		ApiInfo apiInfo = new ApiInfo("User app RESTful Web Service documentation",
				"This pages documents User app RESTful Web Service endpoints", "1.0",
				"http://www.appsdeveloperblof.com/service.html", contact, "Apache 2.0",
				"http://www.apache.org/licenses/LICENSE-2.0", vendorExtensions);

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.generic.xml2csv"))
				.build().apiInfo(apiInfo);
	}
}
