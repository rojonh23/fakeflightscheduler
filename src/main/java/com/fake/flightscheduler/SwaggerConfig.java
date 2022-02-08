package com.fake.flightscheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket flightsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
          .groupName("Flights")
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.fake.flightscheduler.controller"))
          .paths(PathSelectors.regex("/flights.*"))
          .build()
          .apiInfo(apiInfo())
          .useDefaultResponseMessages(false);
    }

	@Bean
    public Docket authApi() {
        return new Docket(DocumentationType.SWAGGER_2)
          .groupName("Authentication")
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.fake.flightscheduler.security.controller"))
          .paths(PathSelectors.regex("/auth.*"))
          .build()
          .apiInfo(apiInfo())
          .useDefaultResponseMessages(false);
    }

	private ApiInfo apiInfo() {
		ApiInfoBuilder infoBuilder = new ApiInfoBuilder();
		infoBuilder
			.title("Flight Booking")
			.description("Flight Booking REST API")
			.version("1.0")
			.contact(new Contact("Rojonh Hernandez", "https://fakeflightbooking.com", "rhernandez@fakeflightbooking.com"))
			.license("GPLv3.0")
			.licenseUrl("https://www.gnu.org/licenses/gpl-3.0.en.html");
        return infoBuilder.build();

   }
}
