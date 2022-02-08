package com.fake.flightscheduler.security.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserResponseDto {
	private Long id;
	private String username;
	private List<String> roles;
}
