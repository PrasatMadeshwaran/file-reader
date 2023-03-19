package com.prasat.filereader;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FileReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileReaderApplication.class, args);
	}

	@Bean
	public OpenAPI createSpringOpenApiDoc() {
		return new OpenAPI()
				.info(new Info().title("File Reader")
						.description("Spring Boot project that allows you to read a CSV file and perform CRUD operations.")
						.version("v1.0.0")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

}
