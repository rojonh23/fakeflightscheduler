package com.fake.flightscheduler;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fake.flightscheduler.security", "com.fake.flightscheduler.repo", "com.fake.flightscheduler.service", "com.fake.flightscheduler.controller" })
public class FlightschedulerApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(FlightschedulerApplication.class, args);
	}

}
