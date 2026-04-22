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

    @GET
    public Response getAllSensors(@QueryParam("type") String type) {

        Collection<Sensor> sensors = DataStore.sensors.values();

        // 🔍 filter by type (like your friend)
        if (type != null && !type.isEmpty()) {
            sensors = sensors.stream()
                    .filter(s -> s.getType().equalsIgnoreCase(type))
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

        // ❌ room must exist
        if (!DataStore.rooms.containsKey(sensor.getRoomId())) {
            return Response.status(Response.Status.UNPROCESSABLE_ENTITY)
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
                )
        ).build();
    }
}