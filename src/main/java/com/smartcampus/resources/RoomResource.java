package com.smartcampus.resources;

import com.smartcampus.models.Room;
import com.smartcampus.store.DataStore;
import com.smartcampus.dto.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collection;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    // GET all rooms
    @GET
    public Response getAllRooms() {
        return Response.ok(
                new ApiResponse<>(
                        true,
                        "Rooms retrieved successfully.",
                        DataStore.rooms.values()
                )
        ).build();
    }

    // POST create room
    @POST
    public Response createRoom(Room room) {

        if (room.getId() == null || room.getId().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse<>(
                            false,
                            "Room ID is required",
                            null
                    ))
                    .build();
        }

        DataStore.rooms.put(room.getId(), room);

        return Response.status(Response.Status.CREATED)
                .entity(new ApiResponse<>(
                        true,
                        "Room created successfully.",
                        room
                ))
                .build();
    }

    // GET single room
    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiResponse<>(
                            false,
                            "Room not found: " + id,
                            null
                    ))
                    .build();
        }

        return Response.ok(
                new ApiResponse<>(
                        true,
                        "Room retrieved successfully.",
                        room
                )
        ).build();
    }

    // DELETE room
    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiResponse<>(
                            false,
                            "Room not found: " + id,
                            null
                    ))
                    .build();
        }

        if (!room.getSensorIds().isEmpty()) {
            return Response.status(422)
                    .entity(new ApiResponse<>(
                            false,
                            "Room has sensors and cannot be deleted",
                            null
                    ))
                    .build();
        }

        DataStore.rooms.remove(id);

        return Response.ok(
                new ApiResponse<>(
                        true,
                        "Room deleted successfully.",
                        null
                )
        ).build();
    }
}