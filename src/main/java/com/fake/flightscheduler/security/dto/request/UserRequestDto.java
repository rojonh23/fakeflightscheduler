package com.fake.flightscheduler.security.dto.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRequestDto {
	@NotBlank
    @Size(min = 3, max = 20)
    private String username;
	
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
    private Set<String> roles;
}
