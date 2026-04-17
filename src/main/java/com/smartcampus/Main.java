package com.smartcampus;

import com.smartcampus.config.AppConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {

    public static void main(String[] args) {

        String BASE_URI = "http://localhost:8080/";

        final ResourceConfig config = ResourceConfig.forApplication(new AppConfig())
                .packages("com.smartcampus");

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI),
                config
        );

        System.out.println("Server running at http://localhost:8080/");

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}