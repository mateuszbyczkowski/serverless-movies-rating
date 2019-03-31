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

public class RateMovieHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger logger = LogManager.getLogger(RateMovieHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        try {
            Map<String, Object> body = (Map<String, Object>) input.get("body");
            Map<String, String> pathParameters = (Map<String, String>) input.get("path");

            String movieId = pathParameters.get("id");

            Movie movie = new Movie().get(movieId);
            if (body.get("rate") instanceof Double) {
                movie.setRate((float) (double) body.get("rate"));
            } else if (body.get("rate") instanceof String) {
                movie.setRate((float) Double.parseDouble((String) body.get("rate")));
            }
            movie.save(movie);

            logger.info("Rate for movie " + movie.getName() + " updated");

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
