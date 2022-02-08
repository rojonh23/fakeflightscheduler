package com.fake.flightscheduler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fake.flightscheduler.controller.FlightController;
import com.fake.flightscheduler.security.controller.AuthController;

@SpringBootTest
class FlightschedulerApplicationTests {

	@Autowired
	private FlightController flightController;
	
	@Autowired
	private AuthController authController;
	
	@Test
	public void contextLoads() {
		assertThat(flightController).isNotNull();
		assertThat(authController).isNotNull();
	}

}
