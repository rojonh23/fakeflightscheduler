package com.fake.flightscheduler.security.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequestDto {
	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
