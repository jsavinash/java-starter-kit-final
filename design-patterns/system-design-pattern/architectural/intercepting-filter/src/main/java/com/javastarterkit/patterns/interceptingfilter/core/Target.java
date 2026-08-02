package com.javastarterkit.patterns.interceptingfilter.core;

import com.javastarterkit.patterns.interceptingfilter.models.Request;
import com.javastarterkit.patterns.interceptingfilter.models.Response;

/**
 * The target handler that ultimately fulfills the request.
 *
 * <p>This is the actual business logic that processes the request
 * after all filters have been executed.
 */
@FunctionalInterface
public interface Target {

    /**
     * Executes the target handler with the given request and response.
     *
     * @param request the incoming request
     * @param response the response to modify
     */
    void execute(Request request, Response response);
}