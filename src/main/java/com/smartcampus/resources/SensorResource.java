package com.smartcampus.resources;

import com.smartcampus.models.Sensor;
import com.smartcampus.store.DataStore;
import com.smartcampus.dto.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collection;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @GET
    public Response getAllSensors(@QueryParam("type") String type) {

        Collection<Sensor> sensors = DataStore.sensors.values();

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

    @POST
    public Response createSensor(Sensor sensor) {

        if (sensor == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse<>(
                            false,
                            "Request body is missing",
                            null
                    ))
                    .build();
        }

        if (sensor.getId() == null || sensor.getId().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse<>(
                            false,
                            "Sensor ID is required",
                            null
                    ))
                    .build();
        }

        if (sensor.getRoomId() == null || sensor.getRoomId().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse<>(
                            false,
                            "roomId is required",
                            null
                    ))
                    .build();
        }

        if (!DataStore.rooms.containsKey(sensor.getRoomId())) {
            return Response.status(422)
                    .entity(new ApiResponse<>(
                            false,
                            "Sensor cannot be created because roomId '" + sensor.getRoomId() + "' does not exist.",
                            null
                    ))
                    .build();
        }

        DataStore.sensors.put(sensor.getId(), sensor);

        DataStore.rooms.get(sensor.getRoomId()).getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED)
                .entity(new ApiResponse<>(
                        true,
                        "Sensor created successfully.",
                        sensor
                ))
                .build();
    }

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

        DataStore.rooms.get(sensor.getRoomId()).getSensorIds().remove(id);
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