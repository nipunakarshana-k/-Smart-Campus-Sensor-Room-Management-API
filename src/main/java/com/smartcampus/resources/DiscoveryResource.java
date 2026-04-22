package com.smartcampus.resources;

import com.smartcampus.dto.ApiResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryResource {

    @GET
    public ApiResponse<Map<String, Object>> getInfo() {

        Map<String, Object> data = new HashMap<>();

        data.put("version", "v1");
        data.put("contact", "admin@university.com");

        Map<String, String> links = new HashMap<>();
        links.put("rooms", "/api/v1/rooms");
        links.put("sensors", "/api/v1/sensors");

        data.put("resources", links);

        return new ApiResponse<>(
                true,
                "Smart Campus API root endpoint",
                data
        );
    }
}