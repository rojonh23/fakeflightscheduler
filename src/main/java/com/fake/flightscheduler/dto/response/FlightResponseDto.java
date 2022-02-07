package com.fake.flightscheduler.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlightResponseDto {

	private String flightNumber;
	private String arrivalPort;
	private String departurePort;
	private String arrivalTime;
	private String departureTime;
}
