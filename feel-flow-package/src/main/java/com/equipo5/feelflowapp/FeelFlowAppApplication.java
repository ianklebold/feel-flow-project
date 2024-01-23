package com.equipo5.feelflowapp;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Feel Flow REST API Documentation",
				description = "This is the REST API Documentation. Created By Ian Fernandez for the Feel Flow Project",
				version = "v1",
				contact = @Contact(
						name = "Ian Fernandez",
						email = "ianstgo@gmail.com",
						url = "https://www.linkedin.com/in/ian-fern%C3%A1ndez-a72598179/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.pryingopenmythirdeye.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Ian Project Accounts microservice REST API Documentation",
				url = "http://pryingopenmythirdeye/swagger-ui.html"
		)
)
public class FeelFlowAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeelFlowAppApplication.class, args);
	}

}
