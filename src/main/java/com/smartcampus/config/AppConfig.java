package com.smartcampus.config;

import com.smartcampus.exceptions.RoomNotEmptyExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {

	public AppConfig() {
		packages("com.smartcampus");
		register(RoomNotEmptyExceptionMapper.class);
	}
}