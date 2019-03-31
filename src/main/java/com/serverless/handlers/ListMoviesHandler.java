package com.serverless.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.Response;
import com.serverless.dal.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMoviesHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger logger = LogManager.getLogger(ListMoviesHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            List<Movie> movies = new Movie().list();

            logger.info("Returning list of movies.");

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(movies)
                    .setHeaders(new HashMap<>())
                    .build();
        } catch (Exception ex) {
            logger.error("Error in listing movies: " + ex);

            Response responseBody = new Response("Error in listing movies: ", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .setHeaders(new HashMap<>())
                    .build();
        }
    }
}
