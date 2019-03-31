package com.serverless.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.Response;
import com.serverless.dal.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class AddMovieHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger logger = LogManager.getLogger(AddMovieHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        try {
            Map<String, String> body = (Map<String, String>) input.get("body");

            Movie movie = new Movie();
            String name = body.get("name");
            movie.setName(name);

            String rate = body.get("rate");
            movie.setRate((float) Double.parseDouble(rate));

            String author = body.get("author");
            movie.setAuthor(author);

            movie.save(movie);

            logger.info(name + " saved successfully.");

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(movie)
                    .setHeaders(new HashMap<>())
                    .build();

        } catch (Exception ex) {
            logger.error("Error in saving movie: " + ex);

            Response responseBody = new Response("Error in saving movie: ", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .setHeaders(new HashMap<>())
                    .build();
        }
    }
}
