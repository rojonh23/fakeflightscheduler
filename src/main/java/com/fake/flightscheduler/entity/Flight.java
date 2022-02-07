package com.fake.flightscheduler.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FLIGHT")
@Entity
public class Flight {

	@Id
	@GeneratedValue(generator = "flightNumberGenerator")
	@GenericGenerator(name = "flightNumberGenerator", strategy="com.fake.flightscheduler.entity.generator.FlightNumberGenerator")
	@Column(name = "FLIGHT_NO", nullable = false, unique = true)
	private String flightNumber;

	@Column(name = "DEPARTURE_PORT", nullable = false)
	private String departurePort;

	@Column(name = "ARRIVAL_PORT", nullable = false)
	private String arrivalPort;

	@Column(name = "DEPARTURE_TIME", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	private OffsetDateTime departureTime;

	@Column(name = "ARRIVAL_TIME", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	private OffsetDateTime arrivalTime;

	@Column(name = "AIRLINE", nullable = false)
	private String airline;

}
