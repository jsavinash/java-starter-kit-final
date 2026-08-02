package com.javastarterkit.patterns.interceptingfilter.filters;

import com.javastarterkit.patterns.interceptingfilter.core.Filter;
import com.javastarterkit.patterns.interceptingfilter.models.Request;
import com.javastarterkit.patterns.interceptingfilter.models.Response;

/**
 * Filter that compresses the response body.
 *
 * <p>This filter simulates gzip-style compression by wrapping the response
 * body with compression tags.
 */
public class CompressionFilter extends Filter {

    @Override
    public boolean before(Request request) {
        System.out.println("  [COMP] Preparing compression for " + request.path());
        return true;
    }

    @Override
    public void after(Request request, Response response) {
        String body = response.body();
        // Simulate gzip-style compression: prefix with <compressed>
        response.body("<compressed>" + body + "</compressed>");
        System.out.println("  [COMP] Compressed response (" + body.length() + " -> "
            + response.body().length() + " chars)");
    }
}