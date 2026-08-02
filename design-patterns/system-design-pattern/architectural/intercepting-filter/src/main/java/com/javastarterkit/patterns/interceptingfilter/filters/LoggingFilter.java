package com.javastarterkit.patterns.interceptingfilter.filters;

import com.javastarterkit.patterns.interceptingfilter.core.Filter;
import com.javastarterkit.patterns.interceptingfilter.models.Request;
import com.javastarterkit.patterns.interceptingfilter.models.Response;

/**
 * Filter that logs requests and responses.
 *
 * <p>This filter logs the incoming request before the target is invoked
 * and logs the outgoing response after the target is invoked.
 */
public class LoggingFilter extends Filter {

    @Override
    public boolean before(Request request) {
        System.out.println("  [LOG]  -> " + request.method() + " " + request.path()
            + " user=" + request.user());
        return true;
    }

    @Override
    public void after(Request request, Response response) {
        System.out.println("  [LOG]  <- status=" + response.status() + " body='" + response.body() + "'");
    }
}