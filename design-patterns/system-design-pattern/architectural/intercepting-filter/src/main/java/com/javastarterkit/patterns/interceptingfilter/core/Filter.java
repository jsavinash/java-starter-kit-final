package com.javastarterkit.patterns.interceptingfilter.core;

import com.javastarterkit.patterns.interceptingfilter.models.Request;
import com.javastarterkit.patterns.interceptingfilter.models.Response;

/**
 * Base for all intercepting filters.
 *
 * <p>Filters can override {@link #before} and/or {@link #after} to perform
 * pre/post-processing. Returning {@code false} from {@link #before} aborts
 * the chain - the target handler will not be invoked.
 *
 * <p>Filters are stateless and thread-safe by default.
 */
public abstract class Filter {

    /**
     * Executes before the target handler.
     *
     * <p>Return {@code true} to continue the filter chain, or {@code false}
     * to abort the chain.
     *
     * @param request the incoming request
     * @return {@code true} to proceed, {@code false} to abort
     */
    public abstract boolean before(Request request);

    /**
     * Executes after the target handler.
     *
     * <p>This method can modify the response.
     *
     * @param request the original request
     * @param response the response to modify
     */
    public void after(Request request, Response response) {
        // Default implementation does nothing
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}