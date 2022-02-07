package com.fake.flightscheduler.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fake.flightscheduler.dto.request.FlightRequestDto;
import com.fake.flightscheduler.dto.response.FlightResponseDto;
import com.fake.flightscheduler.entity.Flight;
import com.fake.flightscheduler.repo.FlightRepository;
import com.fake.flightscheduler.service.FlightService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {

	private final FlightRepository flightRepository;
	private final ModelMapper modelMapper;

	@Override
	public FlightResponseDto save(FlightRequestDto flightRequestDto) {
		Flight flight = flightRepository.save(modelMapper.map(flightRequestDto, Flight.class));
		return modelMapper.map(flight, FlightResponseDto.class);
	}

	@Override
	public List<FlightResponseDto> findAll() {
		return flightRepository.findAll().stream().map(flight -> modelMapper.map(flight, FlightResponseDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<FlightResponseDto> findByAirlines(List<String> airlines) {
		return flightRepository.findByAirlineIn(airlines).stream().map(flight -> modelMapper.map(flight, FlightResponseDto.class))
				.collect(Collectors.toList());
	}

}
