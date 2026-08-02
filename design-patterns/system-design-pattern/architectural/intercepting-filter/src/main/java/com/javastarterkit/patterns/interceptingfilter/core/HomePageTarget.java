package com.javastarterkit.patterns.interceptingfilter.core;

import com.javastarterkit.patterns.interceptingfilter.models.Request;
import com.javastarterkit.patterns.interceptingfilter.models.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The target handler that fulfills the request after all filters pass.
 *
 * <p>This is the actual business logic that processes the request and produces
 * a response. In a web application context, this would correspond to a servlet
 * or controller method. It is stateless and thread-safe.
 *
 * <p>The home page target renders a personalized welcome message for the
 * authenticated user, along with a timestamp and the requested path.
 */
public class HomePageTarget implements Target {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Executes the target handler with the given request and response.
     *
     * <p>Produces a success response (HTTP 200) with a personalized welcome
     * message for the authenticated user.
     *
     * @param request the incoming request
     * @param response the response to modify
     */
    @Override
    public void execute(Request request, Response response) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String body = "Welcome " + request.user() + "! You requested "
                + request.method() + " " + request.path()
                + ". Served at " + timestamp + ".";

        response.status(200).body(body);
        System.out.println("  [TARGET]  Home page rendered for user '" + request.user() + "'");
    }
}
