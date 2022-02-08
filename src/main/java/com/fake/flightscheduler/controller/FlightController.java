package com.fake.flightscheduler.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fake.flightscheduler.dto.request.FlightRequestDto;
import com.fake.flightscheduler.dto.response.FlightResponseDto;

@RequestMapping(value = "/flights")
public interface FlightController {

	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@GetMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	@ResponseBody
	List<FlightResponseDto> getFlights(
			@RequestParam(value = "airlines", required = false) List<String> airlines);

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
	@ResponseBody
	FlightResponseDto saveFlight(@RequestBody FlightRequestDto flightRequestDto);

}
