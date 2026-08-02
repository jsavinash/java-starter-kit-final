package com.javastarterkit.patterns.interceptingfilter.core;

import com.javastarterkit.patterns.interceptingfilter.models.Request;
import com.javastarterkit.patterns.interceptingfilter.models.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the ordered list of filters and invokes them around a target.
 *
 * <p>If any filter's {@code before} returns {@code false}, the chain stops
 * and the target is never reached.
 *
 * <p>This class is thread-safe as long as filters are stateless and the
 * target is stateless or thread-safe.
 */
public class FilterChain {
    private final List<Filter> filters;
    private final Target target;

    /**
     * Creates a new FilterChain with the given target.
     *
     * @param target the target handler
     */
    public FilterChain(Target target) {
        this.target = target;
        this.filters = new ArrayList<>();
    }

    /**
     * Adds a filter to the chain.
     *
     * <p>Filters are executed in the order they are added (before hooks).
     *
     * @param filter the filter to add
     */
    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    /**
     * Processes the request through the filter chain.
     *
     * <p>Execution order:
     * <ol>
     *   <li>Execute before hooks in order (abort if any returns false)</li>
     *   <li>Execute target handler</li>
     *   <li>Execute after hooks in reverse order</li>
     * </ol>
     *
     * @param request the incoming request
     * @return the response
     */
    public Response proceed(Request request) {
        Response response = new Response();

        // Run before-hooks; abort if any filter says "stop"
        for (Filter filter : filters) {
            if (!filter.before(request)) {
                response.status(403).body("Blocked by " + filter);
                return response;
            }
        }

        // Invoke the target handler
        target.execute(request, response);

        // Run after-hooks in reverse order
        for (int i = filters.size() - 1; i >= 0; i--) {
            filters.get(i).after(request, response);
        }

        return response;
    }

    /**
     * Returns an unmodifiable view of the filters in this chain.
     *
     * @return the list of filters
     */
    public List<Filter> getFilters() {
        return Collections.unmodifiableList(filters);
    }
}