package com.smartcampus.resources;

import com.smartcampus.models.Sensor;
import com.smartcampus.store.DataStore;
import com.smartcampus.dto.ApiResponse;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    // GET all sensors (with optional filter)
    @GET
    public Response getAllSensors(@QueryParam("type") String type) {

        Collection<Sensor> sensors = DataStore.sensors.values();

        // filter by type
        if (type != null && !type.isEmpty()) {
            sensors = sensors.stream()
                    .filter(s -> s.getType() != null && s.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());

            return Response.ok(
                    new ApiResponse<>(
                            true,
                            "Sensors filtered by type successfully.",
                            sensors
                    )
            ).build();
        }

        return Response.ok(
                new ApiResponse<>(
                        true,
                        "Sensors retrieved successfully.",
                        sensors
                )
        ).build();
    }

    // POST create sensor
    @POST
    public Response createSensor(Sensor sensor) {

        // check null body
        if (sensor == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse<>(
                            false,
                            "Request body is missing",
                            null
                    ))
                    .build();
        }

        //  validate ID
        if (sensor.getId() == null || sensor.getId().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse<>(
                            false,
                            "Sensor ID is required",
                            null
                    ))
                    .build();
        }

        //  validate roomId
        if (sensor.getRoomId() == null || sensor.getRoomId().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse<>(
                            false,
                            "roomId is required",
                            null
                    ))
                    .build();
        }

        //  room must exist (FIXED — no UNPROCESSABLE_ENTITY)
        if (!DataStore.rooms.containsKey(sensor.getRoomId())) {
            return Response.status(422) // ✅ FIXED HERE
                    .entity(new ApiResponse<>(
                            false,
                            "Sensor cannot be created because roomId '" + sensor.getRoomId() + "' does not exist.",
                            null
                    ))
                    .build();
        }

        //  save sensor
        DataStore.sensors.put(sensor.getId(), sensor);

        // safe link sensor to room
        if (DataStore.rooms.get(sensor.getRoomId()).getSensorIds() != null) {
            DataStore.rooms.get(sensor.getRoomId()).getSensorIds().add(sensor.getId());
        }

        return Response.status(Response.Status.CREATED)
                .entity(new ApiResponse<>(
                        true,
                        "Sensor created successfully.",
                        sensor
                ))
                .build();
    }

    // GET one sensor
    @GET
    @Path("/{id}")
    public Response getSensor(@PathParam("id") String id) {

        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiResponse<>(
                            false,
                            "Sensor not found: " + id,
                            null
                    ))
                    .build();
        }

        return Response.ok(
                new ApiResponse<>(
                        true,
                        "Sensor retrieved successfully.",
                        sensor
                )
        ).build();
    }

    // DELETE sensor
    @DELETE
    @Path("/{id}")
    public Response deleteSensor(@PathParam("id") String id) {

        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiResponse<>(
                            false,
                            "Sensor not found: " + id,
                            null
                    ))
                    .build();
        }

        // remove from room safely
        if (DataStore.rooms.containsKey(sensor.getRoomId())) {
            DataStore.rooms.get(sensor.getRoomId()).getSensorIds().remove(id);
        }

        DataStore.sensors.remove(id);

        return Response.ok(
                new ApiResponse<>(
                        true,
                        "Sensor deleted successfully.",
                        null
                ))
                .build();
    }
}