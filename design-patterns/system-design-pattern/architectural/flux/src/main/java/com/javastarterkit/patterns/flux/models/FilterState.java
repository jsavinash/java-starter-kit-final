package com.javastarterkit.patterns.flux.models;

import java.util.Objects;

/**
 * Immutable state container for the FilterStore.
 *
 * <p>Holds the current visibility filter for the todo list.
 * Being a record, it is inherently immutable and thread-safe.
 *
 * @param filter the current filter; must not be null
 */
public record FilterState(Filter filter) {

    /**
     * Creates a new FilterState with validation.
     *
     * @param filter the filter; must not be null
     * @throws NullPointerException if filter is null
     */
    public FilterState {
        if (filter == null) {
            throw new NullPointerException("filter must not be null");
        }
    }

    /**
     * Returns a new FilterState with the given filter.
     *
     * @param newFilter the new filter; must not be null
     * @return a new FilterState instance
     */
    public FilterState withFilter(final Filter newFilter) {
        return new FilterState(newFilter);
    }

    @Override
    public String toString() {
        return "FilterState{" +
                "filter=" + filter +
                '}';
    }
}