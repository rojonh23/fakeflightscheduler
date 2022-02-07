package com.fake.flightscheduler.service;

import java.util.List;

import com.fake.flightscheduler.dto.request.FlightRequestDto;
import com.fake.flightscheduler.dto.response.FlightResponseDto;

public interface FlightService {
	FlightResponseDto save(FlightRequestDto flightRequestDto);
	List<FlightResponseDto> findAll();
	List<FlightResponseDto> findByAirlines(List<String> airlines);
}
