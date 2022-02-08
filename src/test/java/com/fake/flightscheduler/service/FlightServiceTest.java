package com.fake.flightscheduler.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fake.flightscheduler.dto.request.FlightRequestDto;
import com.fake.flightscheduler.dto.response.FlightResponseDto;
import com.fake.flightscheduler.entity.Flight;
import com.fake.flightscheduler.repo.FlightRepository;
import com.fake.flightscheduler.service.impl.FlightServiceImpl;

@SpringBootTest
public class FlightServiceTest {

	@MockBean
	private FlightRepository flightRepository;

	@MockBean
	private ModelMapper modelMapper;

	private FlightService flightService;

	private List<Flight> mockFlights;

	@BeforeEach
	public void init() {
		flightService = new FlightServiceImpl(flightRepository, modelMapper);
		Flight flight1 = new Flight("QF1", "SYD", "MEL", OffsetDateTime.parse("2020-06-11T18:25:23Z"), OffsetDateTime.parse("2020-06-11T18:55:23Z"), "QF");
		Flight flight2 = new Flight("KY2", "PHI", "MEL", OffsetDateTime.parse("2020-06-11T18:25:23Z"), OffsetDateTime.parse("2020-06-11T18:55:23Z"), "KY");
		Flight flight3 = new Flight("QF3", "PHI", "MEL", OffsetDateTime.parse("2020-06-11T18:25:23Z"), OffsetDateTime.parse("2020-06-11T18:55:23Z"), "QF");
		Flight flight4 = new Flight("PAL4", "PHI", "SYD", OffsetDateTime.parse("2020-06-11T18:25:23Z"), OffsetDateTime.parse("2020-06-11T18:55:23Z"), "PAL");
		mockFlights = new ArrayList<Flight>();
		mockFlights.add(flight1);
		mockFlights.add(flight2);
		mockFlights.add(flight3);
		mockFlights.add(flight4);
	}

	@Test
	public void testFindAllSuccess() {
		Mockito.when(flightRepository.findAll()).thenReturn(mockFlights);
		List<FlightResponseDto> flightDtos = flightService.findAll();
		assertEquals(mockFlights.size(), flightDtos.size());
		Mockito.verify(flightRepository).findAll();
	}

	@Test
	public void testFindByAirlinesSuccess() {
		List<String> airlines = Arrays.asList("QF","PAL");
		List<Flight> filteredResults = mockFlights.stream().filter(flight->airlines.stream().anyMatch(flight.getFlightNumber()::startsWith)).collect(Collectors.toList());
		Mockito.when(flightRepository.findByAirlineIn(Mockito.anyList())).thenReturn(filteredResults);
		List<FlightResponseDto> flightDtos = flightService.findByAirlines(airlines);
		assertEquals(filteredResults.size(), flightDtos.size());
		Mockito.verify(flightRepository).findByAirlineIn(Mockito.anyList());
	}

	@Test
	public void testSaveSuccess() {
		Flight flight = mockFlights.get(0);
		FlightRequestDto flightRequestDto = new FlightRequestDto();
		FlightResponseDto flightResponseDto = new FlightResponseDto(flight.getFlightNumber(), flight.getArrivalPort(), flight.getDeparturePort(), flight.getArrivalTime().toString(), flight.getDepartureTime().toString());
		Mockito.when(modelMapper.map(Mockito.any(FlightRequestDto.class), Mockito.any())).thenReturn(flight);
		Mockito.when(modelMapper.map(Mockito.any(Flight.class), Mockito.any())).thenReturn(flightResponseDto);
		Mockito.when(flightRepository.save(Mockito.any(Flight.class))).thenReturn(flight);
		FlightResponseDto savedFlight = flightService.save(flightRequestDto);
		assertEquals(flight.getFlightNumber(), savedFlight.getFlightNumber());

		assertEquals(flight.getArrivalPort(), savedFlight.getArrivalPort());

		assertEquals(flight.getDeparturePort(), savedFlight.getDeparturePort());

		assertEquals(flight.getDepartureTime().toString(), savedFlight.getDepartureTime());

		assertEquals(flight.getArrivalTime().toString(), savedFlight.getArrivalTime());
	}

	@Test
	public void testFindAllException() {
		Mockito.when(flightRepository.findAll()).thenThrow(new RuntimeException());

		assertThrows(RuntimeException.class, () -> {flightService.findAll();});
		Mockito.verifyNoInteractions(modelMapper);
	}

	@Test
	public void testFindByAirlinesException() {
		Mockito.when(flightRepository.findByAirlineIn(Mockito.anyList())).thenThrow(new RuntimeException());

		assertThrows(RuntimeException.class, () -> {flightService.findByAirlines(Arrays.asList("QF"));});
		Mockito.verifyNoInteractions(modelMapper);
	}

	@Test
	public void testSaveException() {
		Flight flight = mockFlights.get(0);
		FlightRequestDto flightRequestDto = new FlightRequestDto();
		Mockito.when(modelMapper.map(Mockito.any(FlightRequestDto.class), Mockito.any())).thenReturn(flight);
		Mockito.when(flightRepository.save(Mockito.any(Flight.class))).thenThrow(new RuntimeException());

		assertThrows(RuntimeException.class, () -> {flightService.save(flightRequestDto);});
		Mockito.verify(modelMapper, Mockito.times(1)).map(Mockito.any(FlightRequestDto.class), Mockito.any());
	}



}
