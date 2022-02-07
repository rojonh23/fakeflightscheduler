package com.fake.flightscheduler.util;

import java.io.IOException;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {
	@Override
	public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		return OffsetDateTime.parse(p.getText());
	}
}
