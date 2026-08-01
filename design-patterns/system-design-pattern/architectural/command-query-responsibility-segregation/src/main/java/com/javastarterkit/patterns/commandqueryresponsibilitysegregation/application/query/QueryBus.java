package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.exception.HandlerNotFoundException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Thread-safe mediator that routes queries to their registered handlers
 * and returns typed results.
 *
 * <h3>Design Notes</h3>
 * <ul>
 *   <li>Like {@link com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command.CommandBus
 *       CommandBus}, handlers are registered at start-up and looked up lock-free
 *       via {@link ConcurrentHashMap} at dispatch time.</li>
 *   <li>The generic return type {@code R} flows through the {@link Query} type
 *       token, so {@code dispatch(new FindAccountById(id))} returns
 *       {@code Optional<AccountView>} with no client-side cast.</li>
 * </ul>
 *
 * @param <R> the result type for the dispatched query — inferred from
 *            {@code query.getClass()} at the call site
 */
public final class QueryBus {

    /** Maps each query type to its handler. */
    private final ConcurrentMap<Class<?>, QueryHandler<?, ?>> handlers = new ConcurrentHashMap<>();

    /**
     * Registers a handler for the given query type.
     *
     * @param queryType the query class this handler handles
     * @param handler   the handler instance
     * @param <Q>       the query type
     * @param <R>       the result type
     */
    public <Q extends Query<R>, R> void register(Class<Q> queryType, QueryHandler<Q, R> handler) {
        handlers.put(queryType, handler);
    }

    /**
     * Dispatches a query to its registered handler and returns the typed result.
     *
     * @param query the query to dispatch
     * @param <R>   the expected result type (inferred from the {@link Query} subtype)
     * @return the query result
     * @throws HandlerNotFoundException if no handler is registered for the query type
     */
    public <R> R dispatch(Query<R> query) {
        var handler = handlers.get(query.getClass());
        if (handler == null) {
            throw new HandlerNotFoundException(
                    "No handler registered for query: " + query.getClass().getSimpleName());
        }
        return invokeHandler(handler, query);
    }

    /**
     * Invokes the handler for the given query.
     *
     * <p>The raw-type cast is safe because type erasure makes
     * {@code QueryHandler<Q, R>.handle(Q)} resolve to
     * {@code handle(Query)} returning {@code Object} at the bytecode level.
     * The result is always of the type {@code R} declared by the query's
     * sealed hierarchy.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <R> R invokeHandler(QueryHandler handler, Query<R> query) {
        return (R) handler.handle(query);
    }
}
