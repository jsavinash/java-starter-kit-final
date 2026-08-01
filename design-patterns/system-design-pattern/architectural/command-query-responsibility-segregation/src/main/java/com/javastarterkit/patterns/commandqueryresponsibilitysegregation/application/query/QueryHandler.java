package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query;

/**
 * Contract for a query handler.
 *
 * <p>Each handler is responsible for exactly one {@link Query} type and
 * returns a result of type {@code R}. Handlers read exclusively from the
 * query-side read model and never mutate domain state.
 *
 * @param <Q> the query type this handler processes
 * @param <R> the result type produced by this handler
 */
public interface QueryHandler<Q extends Query<R>, R> {

    /**
     * Processes the given query and returns the result.
     *
     * @param query the query to handle (never {@code null})
     * @return the query result
     */
    R handle(Q query);
}
