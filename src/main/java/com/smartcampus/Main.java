package com.smartcampus;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import java.net.URI;

public class Main {

    public static void main(String[] args) {

        String BASE_URI = "http://localhost:8080/";

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI)
        );

        System.out.println("Server running at http://localhost:8080/");
        
        try {
            Thread.currentThread().join(); // keeps server running
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}