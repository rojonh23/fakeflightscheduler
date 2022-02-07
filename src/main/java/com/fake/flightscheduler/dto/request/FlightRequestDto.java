package com.fake.flightscheduler.dto.request;
import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;

import com.fake.flightscheduler.util.OffsetDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlightRequestDto {

	@NotBlank
	private String arrivalPort;

	@NotBlank
	private String departurePort;

	@NotBlank
	@JsonDeserialize(using = OffsetDateTimeDeserializer.class)
	private OffsetDateTime arrivalTime;

	@NotBlank
	@JsonDeserialize(using = OffsetDateTimeDeserializer.class)
	private OffsetDateTime departureTime;

	@NotBlank
	private String airline;

}
