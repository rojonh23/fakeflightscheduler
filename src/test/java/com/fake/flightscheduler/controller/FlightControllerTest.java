package com.fake.flightscheduler.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import com.fake.flightscheduler.dto.request.FlightRequestDto;
import com.fake.flightscheduler.dto.response.FlightResponseDto;
import com.fake.flightscheduler.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FlightService flightService;

	private List<FlightResponseDto> mockResponseDtos;

	private ObjectMapper mapper;

	@BeforeEach
	public void initData() {
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
		FlightResponseDto flight1 = new FlightResponseDto("QF1", "SYD", "MEL", "2020-06-11T18:25:23+08:00", "2020-06-11T18:55:23+08:00");
		FlightResponseDto flight2 = new FlightResponseDto("KY2", "PHI", "MEL", "2020-06-11T18:25:23+08:00", "2020-06-11T18:55:23+08:00");
		FlightResponseDto flight3 = new FlightResponseDto("QF3", "PHI", "MEL", "2020-06-11T18:25:23+08:00", "2020-06-11T18:55:23+08:00");
		FlightResponseDto flight4 = new FlightResponseDto("PAL4", "PHI", "SYD", "2020-06-11T18:25:23+08:00", "2020-06-11T18:55:23+08:00");
		mockResponseDtos = new ArrayList<FlightResponseDto>();
		mockResponseDtos.add(flight1);
		mockResponseDtos.add(flight2);
		mockResponseDtos.add(flight3);
		mockResponseDtos.add(flight4);
	}


	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void testGetAllFlights() throws Exception {
		Mockito.when(flightService.findAll()).thenReturn(mockResponseDtos);
		String jsonResponse = mapper.writeValueAsString(mockResponseDtos);
		this.mockMvc.perform(get("/flights")).andDo(print()).andExpect(content().json(jsonResponse)).andExpect(status().isOk()).andReturn();
	}

	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void testGetFlightsbyAirlines() throws Exception {
		List<String> airlines = Arrays.asList("QF","PAL");
		List<FlightResponseDto> filteredResult = mockResponseDtos.stream().filter(dto -> airlines.stream().anyMatch(dto.getFlightNumber()::startsWith)).collect(Collectors.toList());
		Mockito.when(flightService.findByAirlines(Mockito.anyList())).thenReturn(filteredResult);
		String jsonResponse = mapper.writeValueAsString(filteredResult);
		this.mockMvc.perform(get("/flights?airlines="+StringUtils.arrayToCommaDelimitedString(airlines.toArray()))).andDo(print()).andExpect(content().json(jsonResponse)).andExpect(status().isOk()).andReturn();
	}

	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void testGetFlightsbyEmptyAirline() throws Exception {
		Mockito.when(flightService.findAll()).thenReturn(mockResponseDtos);
		String jsonResponse = mapper.writeValueAsString(mockResponseDtos);
		this.mockMvc.perform(get("/flights?airlines=")).andDo(print()).andExpect(content().json(jsonResponse)).andExpect(status().isOk()).andReturn();
	}

	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	public void testSaveFlightSuccess() throws Exception {
		FlightRequestDto flightReq = new FlightRequestDto("SYD", "PHL", OffsetDateTime.now(ZoneId.of("UTC")), OffsetDateTime.now(ZoneId.of("UTC")).plusHours(2), "QF");
		FlightResponseDto flightResp = new FlightResponseDto("QF5", "SYD", "PHL", "2020-06-11T18:25:23+08:00", "2020-06-11T18:55:23+08:00");
		Mockito.when(flightService.save(Mockito.any(FlightRequestDto.class))).thenReturn(flightResp);
		String jsonRequest = mapper.writeValueAsString(flightReq);
		this.mockMvc.perform(post("/flights").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	@WithMockUser(username="user",roles={"USER"})
	public void testSaveFlightForbidden() throws Exception {
		FlightRequestDto flightReq = new FlightRequestDto("SYD", "PHL", OffsetDateTime.now(ZoneId.of("UTC")), OffsetDateTime.now(ZoneId.of("UTC")).plusHours(2), "QF");
		FlightResponseDto flightResp = new FlightResponseDto("QF5", "SYD", "PHL", "2020-06-11T18:25:23+08:00", "2020-06-11T18:55:23+08:00");
		Mockito.when(flightService.save(Mockito.any(FlightRequestDto.class))).thenReturn(flightResp);
		String jsonRequest = mapper.writeValueAsString(flightReq);
		this.mockMvc.perform(post("/flights").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andDo(print()).andExpect(status().isForbidden()).andReturn();
	}
}

