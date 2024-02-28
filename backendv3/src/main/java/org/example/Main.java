package org.example;

import com.google.gson.Gson;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        // Set port
        port(8080);


        // Enable CORS
        enableCORS("*", "*", "*");

        // Handle POST requests
        post("/word-count-api", (request, response) -> {
            String body = request.body(); // Get the body of the request
            // Convert JSON string to Java object
            Gson gson = new Gson();
            RequestBodyObject requestBodyObject = gson.fromJson(body, RequestBodyObject.class);

            // Access numThreads and fileName
            int numThreads = requestBodyObject.getNumThreads();
            String fileName = requestBodyObject.getFileName();

            // Process the data as needed
            System.out.println("Received numThreads: " + numThreads);
            System.out.println("Received fileName: " + fileName);

            // Process the body as needed
            return "successful api call";
        });
    }

    // Enable CORS
    private static void enableCORS(final String origin, final String methods, final String headers) {
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            response.type("application/json");
        });
    }


}