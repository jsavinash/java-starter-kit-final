package com.javastarterkit.patterns.interceptingfilter.core;

import com.javastarterkit.patterns.interceptingfilter.models.Request;
import com.javastarterkit.patterns.interceptingfilter.models.Response;

import java.util.Objects;

/**
 * Entry point for the Intercepting Filter pattern.
 *
 * <p>The FilterManager owns the {@link FilterChain} and exposes a simple
 * {@link #process(Request)} method that clients call to have their request
 * processed through the configured filter pipeline.
 *
 * <p>This class is thread-safe as long as the underlying {@link FilterChain}
 * and its components (filters, target) are thread-safe. The FilterManager
 * itself holds no mutable state beyond the immutable reference to its
 * FilterChain.
 *
 * @see FilterChain
 * @see Filter
 * @see Target
 */
public class FilterManager {

    private final FilterChain filterChain;

    /**
     * Creates a new FilterManager with the given target handler.
     *
     * @param target the target handler that fulfills filtered requests
     * @throws IllegalArgumentException if target is {@code null}
     */
    public FilterManager(Target target) {
        Objects.requireNonNull(target, "Target cannot be null");
        this.filterChain = new FilterChain(target);
    }

    /**
     * Adds a filter to the processing chain.
     *
     * <p>Filters are executed in the order they are added (before hooks),
     * and their after-hooks run in reverse order (stack-like semantics).
     *
     * @param filter the filter to add; must not be {@code null}
     * @throws IllegalArgumentException if filter is {@code null}
     */
    public void addFilter(Filter filter) {
        Objects.requireNonNull(filter, "Filter cannot be null");
        filterChain.addFilter(filter);
    }

    /**
     * Processes the request through the filter chain.
     *
     * <p>This is the main entry point for clients. The request flows through
     * all configured filters (before hooks, target execution, after hooks)
     * and the resulting response is returned.
     *
     * @param request the incoming request; must not be {@code null}
     * @return the response produced by the filter chain
     * @throws IllegalArgumentException if request is {@code null}
     */
    public Response process(Request request) {
        Objects.requireNonNull(request, "Request cannot be null");
        return filterChain.proceed(request);
    }

    /**
     * Returns the underlying filter chain.
     *
     * @return the filter chain
     */
    protected FilterChain getFilterChain() {
        return filterChain;
    }
}
