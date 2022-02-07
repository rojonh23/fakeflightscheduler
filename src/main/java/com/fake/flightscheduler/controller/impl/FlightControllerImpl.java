package com.fake.flightscheduler.controller.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.fake.flightscheduler.controller.FlightController;
import com.fake.flightscheduler.dto.request.FlightRequestDto;
import com.fake.flightscheduler.dto.response.FlightResponseDto;
import com.fake.flightscheduler.service.FlightService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FlightControllerImpl implements FlightController {

	private final FlightService flightService;

	@Override
	public List<FlightResponseDto> getFlights(List<String> airlines) {
		if(null == airlines || airlines.isEmpty()) {
			return flightService.findAll();
		}
		return flightService.findByAirlines(airlines);
	}

	@Override
	public FlightResponseDto saveFlight(FlightRequestDto flightRequestDto) {
		return flightService.save(flightRequestDto);
	}

}
