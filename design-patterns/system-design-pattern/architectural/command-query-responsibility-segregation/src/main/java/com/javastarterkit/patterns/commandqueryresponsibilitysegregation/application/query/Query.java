package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query;

import java.util.List;

/**
 * Sealed base type for all queries in the CQRS read model.
 *
 * <p>A <i>query</i> is an intent to read data. Queries are processed by the
 * {@link QueryBus} which routes each query to its registered {@link QueryHandler}.
 * The type parameter {@code R} declares the result type, giving full
 * compile-time type safety: {@code QueryBus.dispatch(FindAccountById)} returns
 * {@code Optional<AccountView>} without any client-side cast.
 *
 * @param <R> the result type produced by the query's handler
 */
public sealed interface Query<R>
        permits FindAccountById, ListAllAccounts, CountAccounts {
}
