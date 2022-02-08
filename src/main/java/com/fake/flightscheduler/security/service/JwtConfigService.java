package com.fake.flightscheduler.security.service;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="jwt-config")
public class JwtConfigService {

	@NotNull
	private String cookieName;

	@NotNull
	private String secretKey;

	@NotNull
	private String expirationMs;
}
